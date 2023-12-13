package com.travelserviceapi.travelserviceapi.service.impl;

import com.travelserviceapi.travelserviceapi.dto.core.UserDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestUserDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseUserDto;
import com.travelserviceapi.travelserviceapi.entity.User;
import com.travelserviceapi.travelserviceapi.exception.DuplicateEntryException;
import com.travelserviceapi.travelserviceapi.repo.UserRepo;
import com.travelserviceapi.travelserviceapi.service.UserService;
import com.travelserviceapi.travelserviceapi.util.Generator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final ModelMapper mapper;

    private final Generator generator;


    public UserServiceImpl(UserRepo userRepo, ModelMapper mapper, Generator generator) {
        this.userRepo = userRepo;
        this.mapper = mapper;
        this.generator = generator;
    }

    @Override
    public ResponseUserDto saveUser(RequestUserDto dto) throws IOException {
        String generatePrefix = generator.generatePrefix(5, 16);
        String primaryKey = generator.generatePrimaryKey("USER", generatePrefix);
        UserDto  userDto = mapper.map(dto, UserDto.class);
        userDto.setUserId(primaryKey);
        if(!userRepo.existsById(userDto.getUserId())){
            User user = mapper.map(userDto, User.class);
            exportImages(userDto,user);
            User save = userRepo.save(user);
            return new ResponseUserDto(save.getUserId(),save.getUsername(),save.getUserPassword(),save.getUserNic(),save.getUserDob(),save.getUserGender(),save.getUserContact(),save.getUserEmail(),save.getUserAddress(),null,null,null,null);

        }else {
            throw new DuplicateEntryException("Duplicate Primary key");
        }

    }


    public void exportImages(UserDto dto,User user) throws IOException {
        String dt = LocalDate.now().toString().replace("-", "_") + "__"
                + LocalTime.now().toString().replace(":", "_");

        if (dto.getProfilePicByte() != null){
            InputStream is = new ByteArrayInputStream(dto.getProfilePicByte());
            BufferedImage bi = ImageIO.read(is);
            File outputfile = new File("images/user/pro/"+dt+ ".jpg");
            ImageIO.write(bi, "jpg", outputfile);
            user.setUserProfilePic(outputfile.getAbsolutePath());
        }

        if (dto.getNicFrontImgByte() != null){
            InputStream is1 = new ByteArrayInputStream(dto.getNicFrontImgByte());
            BufferedImage bi1 = ImageIO.read(is1);
            File outputfile1 = new File("images/user/nic_front/"+dt+ ".jpg");
            ImageIO.write(bi1, "jpg", outputfile1);
            user.setUserNicFrontImg(outputfile1.getAbsolutePath());
        }

        if (dto.getNicRearImgByte() != null){
            InputStream is2 = new ByteArrayInputStream(dto.getNicRearImgByte());
            BufferedImage bi2 = ImageIO.read(is2);
            File outputfile2 = new File("images/user/nic_back/"+dt+ ".jpg");
            ImageIO.write(bi2, "jpg", outputfile2);
            user.setUserNicRearImg(outputfile2.getAbsolutePath());
        }

    }
}
