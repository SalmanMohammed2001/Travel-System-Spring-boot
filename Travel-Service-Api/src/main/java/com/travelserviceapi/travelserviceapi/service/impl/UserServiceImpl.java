package com.travelserviceapi.travelserviceapi.service.impl;

import com.travelserviceapi.travelserviceapi.dto.core.UserDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestUserDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseUserDto;
import com.travelserviceapi.travelserviceapi.embadded.Contact;
import com.travelserviceapi.travelserviceapi.entity.User;
import com.travelserviceapi.travelserviceapi.entity.UserRole;
import com.travelserviceapi.travelserviceapi.exception.DuplicateEntryException;
import com.travelserviceapi.travelserviceapi.exception.EntryNotFoundException;
import com.travelserviceapi.travelserviceapi.repo.UserRepo;
import com.travelserviceapi.travelserviceapi.repo.UserRoleRepo;
import com.travelserviceapi.travelserviceapi.service.UserService;
import com.travelserviceapi.travelserviceapi.service.process.impl.EmailService;
import com.travelserviceapi.travelserviceapi.util.Generator;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final ModelMapper mapper;

    private final Generator generator;
    
    private final UserRoleRepo userRoleRepo;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepo userRepo, ModelMapper mapper, Generator generator, UserRoleRepo userRoleRepo, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.mapper = mapper;
        this.generator = generator;
        this.userRoleRepo = userRoleRepo;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseUserDto saveUser(RequestUserDto dto) throws IOException {

        String generatePrefix = generator.generatePrefix(5, 16);

        if(userRepo.findByPrefix(generatePrefix).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }


        Optional<UserRole> selectUserRole = userRoleRepo.findByRoleName("USER");

        if(selectUserRole.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }



        String primaryKey = generator.generatePrimaryKey(generatePrefix, "U");
        String verificationCode=generator.createVerificationCode();
        /*send email=====>*/

        if( emailService.createEmails(dto.getEmail(),
                "Regarding Login",

                "<h1>Verification Code ="+verificationCode+"</h1>"
        )){

            //save data
              UserDto userDto = mapper.map(dto, UserDto.class);
              userDto.setPassword(passwordEncoder.encode(dto.getPassword()));
              userDto.setPrefix(generatePrefix);
        userDto.setUserState(true);
        userDto.setUserId(primaryKey);

        if(!userRepo.existsByUserEmail(dto.getEmail())){

            if (!userRepo.existsById(userDto.getUserId())) {
                User user = mapper.map(userDto, User.class);
                user.setAccountNonExpired(true);
                user.setAccountNonLock(true);
                user.setCredentialsNoExpired(true);
                user.setEnable(false);
                user.setOtp(verificationCode);
                user.setUserRole(selectUserRole.get());
                exportImages(userDto, user);
                User save = userRepo.save(user);

                return new ResponseUserDto(save.getUserId(), save.getUsername(), save.getUserPassword(), save.getUserNic(),
                        save.getUserDob(), save.getUserGender(), save.getUserContact(), save.getUserEmail(),
                        save.getUserAddress(),null,null, null, save.isUserState(), null,null,null,null);

            } else {
                throw new DuplicateEntryException("Duplicate Primary key");
            }

        }else {
            throw new EntryNotFoundException("Duplicate email key");
        }


        }else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

       /* UserDto userDto = mapper.map(dto, UserDto.class);
        userDto.setUserState(true);
        userDto.setUserId(primaryKey);

        if(!userRepo.existsByUserEmail(dto.getEmail())){

            if (!userRepo.existsById(userDto.getUserId())) {
                User user = mapper.map(userDto, User.class);
                exportImages(userDto, user);
                User save = userRepo.save(user);

                return new ResponseUserDto(save.getUserId(), save.getUsername(), save.getUserPassword(), save.getUserNic(),
                        save.getUserDob(), save.getUserGender(), save.getUserContact(), save.getUserEmail(),
                        save.getUserAddress(),null,null, null, save.isUserState(), null,null,null,null);

            } else {
                throw new DuplicateEntryException("Duplicate Primary key");
            }

        }else {
            throw new EntryNotFoundException("Duplicate email key");
        }*/



    }

    @Override
    public ResponseUserDto findByUser(String email) throws IOException {
        if(userRepo.existsByUserEmail(email)){
            User user = userRepo.findByUserEmail(email);
            ResponseUserDto responseUserDto = mapper.map(user, ResponseUserDto.class);
            importImages(responseUserDto,user);
            return responseUserDto;
        }else {
            throw new EntryNotFoundException("User Not found");
        }
    }

    @Override
    public ResponseUserDto updateUser(RequestUserDto dto) throws IOException {
        if(userRepo.existsByUserEmail(dto.getEmail())){

            UserDto userDto = mapper.map(dto, UserDto.class);
            User byUserEmail = userRepo.findByUserEmail(userDto.getEmail());
            deleteImages(userDto,byUserEmail);
            User user = mapper.map(userDto, User.class);
            exportImages(userDto, user);
            Contact contact = new Contact(user.getUserContact().getContact1(),user.getUserContact().getContact2());
            userRepo.updateUser(
                    user.getUsername(),
                    user.getUserPassword(),
                    user.getUserAddress(),
                    user.getUserDob(), user.getUserGender(),
                    user.getUserNic(), user.getUserNicFrontImg(),
                    user.getUserNicRearImg(),
                    user.getUserProfilePic(),
                    new Contact(user.getUserContact().getContact1(), user.getUserContact().getContact2()),
                    user.getUserEmail());

        }else {
            throw new EntryNotFoundException("User Not found");
        }

        return null;
    }

    @Override
    public void deleteUser(String email) {

        User user = userRepo.findByUserEmail(email);
       deleteImagesByEmail(user);
        userRepo.deleteUserByUserEmail(email);
    }

    @Override
    public List<ResponseUserDto> findAllUser() throws IOException {
        List<User> user = userRepo.findAll();
        /*user.forEach(data->{
            System.out.println(data.getUserId());
        });*/
        if(user.isEmpty()){
            return null;
        }else {
            List<ResponseUserDto> responseUserDtos = mapper.map(user, new TypeToken<List<ResponseUserDto>>() {}.getType());
            List<ResponseUserDto> responseUserDtos1 = importImagesAll(responseUserDtos, user);
            return responseUserDtos1;
        }

    }

    @Override
    public List<ResponseUserDto> searchByEmailAndName(String email,String nic) {
        List<User> users = userRepo.searchByEmailAndName(email,nic);
        ArrayList<ResponseUserDto> responseUserDtos = new ArrayList<>();

        users.forEach(data->{
            responseUserDtos.add(new ResponseUserDto( data.getUserId(),data.getUsername(),data.getUserPassword(),data.getUserNic(),
                    data.getUserDob(),data.getUserGender(),data.getUserContact(), data.getUserEmail(),
                    data.getUserAddress(),null,null, null,data.isUserState(), null,null,null,null
            ));
        });
        return  responseUserDtos;

    }

    @Override
    public void VerifyAccount(String email, String otp) {
        User byUserEmail = userRepo.findByUserEmail(email);
        if(!userRepo.existsByUserEmail(email)){
            throw new EntryNotFoundException("User Not found");
        }
        if(byUserEmail.isEnable()) throw new ResponseStatusException(HttpStatus.OK);
        if (byUserEmail.getOtp().equals(otp)){
            byUserEmail.setEnable(true);
            userRepo.save(byUserEmail);
        }else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }


    public void exportImages(UserDto dto, User user) throws IOException {
        String dt = LocalDate.now().toString().replace("-", "_") + "__"
                + LocalTime.now().toString().replace(":", "_");

        if (dto.getProfilePicByte() != null) {
            InputStream is = new ByteArrayInputStream(dto.getProfilePicByte());
            BufferedImage bi = ImageIO.read(is);
           File outputfile = new File("images/user/pic/" + dt + ".jpg");
           ImageIO.write(bi, "jpg", outputfile);
            user.setUserProfilePic(outputfile.getAbsolutePath());
        }

        if (dto.getNicFrontImgByte() != null) {
            InputStream is1 = new ByteArrayInputStream(dto.getNicFrontImgByte());
            BufferedImage bi1 = ImageIO.read(is1);
            File outputfile1 = new File("images/user/nic_front/" + dt + ".jpg");
            ImageIO.write(bi1, "jpg", outputfile1);
            user.setUserNicFrontImg(outputfile1.getAbsolutePath());
        }

        if (dto.getNicRearImgByte() != null) {
            InputStream is2 = new ByteArrayInputStream(dto.getNicRearImgByte());
            BufferedImage bi2 = ImageIO.read(is2);
            File outputfile2 = new File("images/user/nic_rear/" + dt + ".jpg");
            ImageIO.write(bi2, "jpg", outputfile2);
            user.setUserNicRearImg(outputfile2.getAbsolutePath());
        }

    }

    public void importImages(ResponseUserDto dto,User user) throws IOException {

        BufferedImage read = ImageIO.read(new File(user.getUserProfilePic()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        dto.setProfilePic(Base64.getEncoder().encodeToString(bytes));
        dto.setProfilePicByte(bytes);

        read = ImageIO.read(new File(user.getUserNicFrontImg()));
        baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        bytes = baos.toByteArray();
        dto.setNicFrontImg(Base64.getEncoder().encodeToString(bytes));
        dto.setNicFrontImgByte(bytes);

        read = ImageIO.read(new File(user.getUserNicRearImg()));
        baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        bytes = baos.toByteArray();
        dto.setNicRearImg(Base64.getEncoder().encodeToString(bytes));
        dto.setNicRearImgByte(bytes);
    }

    public List<ResponseUserDto> importImagesAll(List<ResponseUserDto> dto,List<User> users) throws IOException {
        User user = new User();
        users.forEach(data->{
            user.setUserProfilePic(data.getUserProfilePic());
            user.setUserNicFrontImg(data.getUserNicFrontImg());
            user.setUserNicRearImg(data.getUserNicRearImg());
        });

        System.out.println(user.getUserProfilePic());
        ResponseUserDto responseUserDto = new ResponseUserDto();;
        BufferedImage read = ImageIO.read(new File(user.getUserProfilePic()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        responseUserDto.setProfilePic(Base64.getEncoder().encodeToString(bytes));
        responseUserDto.setProfilePicByte(bytes);



        read = ImageIO.read(new File(user.getUserNicFrontImg()));
        baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        bytes = baos.toByteArray();
        responseUserDto.setNicFrontImg(Base64.getEncoder().encodeToString(bytes));
        responseUserDto.setNicFrontImgByte(bytes);

        read = ImageIO.read(new File(user.getUserNicRearImg()));
        baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        bytes = baos.toByteArray();
        responseUserDto.setNicRearImg(Base64.getEncoder().encodeToString(bytes));
        responseUserDto.setNicRearImgByte(bytes);



        List<ResponseUserDto> response =new ArrayList<>();
        for(ResponseUserDto dto1:dto){
            response.add(new ResponseUserDto(dto1.getUserId(),dto1.getUsername(),dto1.getPassword(),
                    dto1.getNic(),dto1.getDob(),dto1.getGender(),dto1.getContact(),dto1.getEmail()
            ,dto1.getAddress(),responseUserDto.getNicFrontImg(),responseUserDto.getNicRearImg(),
                    responseUserDto.getProfilePic(),dto1.isUserState(),responseUserDto.getNicFrontImgByte(),
                    responseUserDto.getNicRearImgByte(),responseUserDto.getProfilePicByte(),null));
        }

        return  response;
    }


    private void deleteImages(UserDto userDTO, User user) {
        if (userDTO.getProfilePicByte()!=null){
            System.out.println("not null");
            boolean delete = new File(user.getUserProfilePic()).delete();
        }
        if (userDTO.getNicFrontImgByte()!=null){
            System.out.println("not null");
            boolean delete = new File(user.getUserNicFrontImg()).delete();
        }
        if (userDTO.getNicRearImgByte()!=null){
            System.out.println("not null");
            boolean delete = new File(user.getUserNicRearImg()).delete();
        }
    }

    private void deleteImagesByEmail(User user) {

            boolean delete1 = new File(user.getUserProfilePic()).delete();

            boolean delete2 = new File(user.getUserNicFrontImg()).delete();

            boolean delete3= new File(user.getUserNicRearImg()).delete();
        }


}
