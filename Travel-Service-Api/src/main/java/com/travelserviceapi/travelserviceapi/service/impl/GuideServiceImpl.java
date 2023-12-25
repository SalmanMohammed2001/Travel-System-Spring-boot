package com.travelserviceapi.travelserviceapi.service.impl;

import com.google.gson.Gson;
import com.travelserviceapi.travelserviceapi.dto.core.DriverDto;
import com.travelserviceapi.travelserviceapi.dto.core.GuideDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestGuideDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseGuideDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseUserDto;
import com.travelserviceapi.travelserviceapi.entity.Driver;
import com.travelserviceapi.travelserviceapi.entity.Guide;
import com.travelserviceapi.travelserviceapi.entity.User;
import com.travelserviceapi.travelserviceapi.exception.DuplicateEntryException;
import com.travelserviceapi.travelserviceapi.exception.EntryNotFoundException;
import com.travelserviceapi.travelserviceapi.repo.GuideRepo;
import com.travelserviceapi.travelserviceapi.service.GuideService;
import com.travelserviceapi.travelserviceapi.util.Generator;
import com.travelserviceapi.travelserviceapi.util.mapper.GuideMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;

@Service

public class GuideServiceImpl implements GuideService {

    private final ModelMapper mapper;

    private final Generator generator;
    private final Gson gson;
    
    private final GuideMapper guideMapper;
    private final GuideRepo guideRepo;
    public GuideServiceImpl(ModelMapper mapper, Generator generator, Gson gson, GuideMapper guideMapper, GuideRepo guideRepo) {
        this.mapper = mapper;
        this.generator = generator;
        this.gson = gson;
        this.guideMapper = guideMapper;
        this.guideRepo = guideRepo;
    }

    @Override
    public ResponseGuideDto saveGuide(RequestGuideDto dto) throws IOException {
        String generateKey = generator.generateKey("Guide");

       GuideDto guideDto = new GuideDto(
               generateKey,
               dto.getGuideName(),
               dto.getGuideAddress(),
               dto.getGuideContact(),
               dto.getGuideBirthDate(),
               dto.getGuideManDayValue(),
               dto.getGuideExperience(),
               dto.getGuideIdFrontImage(),
               dto.getGuideIdRearImage(),
               dto.getGuideNicFrontImag(),
               dto.getGuideNicRearImage(),
               dto.getGuideProfilePicImage(),
               dto.isGuideStatus()
       );

       if(!guideRepo.existsById(guideDto.getGuideId())){
           Guide guide = mapper.map(guideDto, Guide.class);
           exportImages(guideDto,guide);
           Guide save = guideRepo.save(guide);
           ResponseGuideDto responseGuideDto = mapper.map(save, ResponseGuideDto.class);
           importImages(responseGuideDto,save);
           return responseGuideDto;


       }else {
            throw new DuplicateEntryException("duplicate Guide Id");
        }

    }

    @Override
    public ResponseGuideDto findId(String guideId) throws IOException {
        if(guideRepo.existsById(guideId)){
            Guide guide = guideRepo.findById(guideId).get();
            ResponseGuideDto responseGuideDto = mapper.map(guide, ResponseGuideDto.class);
            importImages(responseGuideDto,guide);

            return responseGuideDto;
        }else {
            throw new EntryNotFoundException("Id Not found");
        }
    }

    @Override
    public ResponseGuideDto update(RequestGuideDto dto) throws IOException {
        GuideDto guideDto = new GuideDto(
                dto.getGuideId(),
                dto.getGuideName(),
                dto.getGuideAddress(),
                dto.getGuideContact(),
                dto.getGuideBirthDate(),
                dto.getGuideManDayValue(),
                dto.getGuideExperience(),
                dto.getGuideIdFrontImage(),
                dto.getGuideIdRearImage(),
                dto.getGuideNicFrontImag(),
                dto.getGuideNicRearImage(),
                dto.getGuideProfilePicImage(),
                dto.isGuideStatus()
        );


        if(guideRepo.existsById(guideDto.getGuideId())){
            Guide guide = mapper.map(guideDto, Guide.class);
            Guide byId = guideRepo.findById(guide.getGuideId()).get();
            deleteImages(guideDto,byId);
            exportImages(guideDto,guide);
            guideRepo.save(guide);
            return null;
        }else {
            throw new EntryNotFoundException("Id Not found");
        }
    }


    public void exportImages(GuideDto dto, Guide guide) throws IOException {
        String dt = LocalDate.now().toString().replace("-", "_") + "__"
                + LocalTime.now().toString().replace(":", "_");

        if (dto.getGuideIdFrontImage() != null) {
            InputStream is = new ByteArrayInputStream(dto.getGuideIdFrontImage());
            BufferedImage bi = ImageIO.read(is);
            File outputfile = new File("images/guide/guide_id_front/" + dt + ".jpg");
            ImageIO.write(bi, "jpg", outputfile);
            guide.setGuideIdFrontImage(outputfile.getAbsolutePath());
        }

        if (dto.getGuideIdRearImage() != null) {
            InputStream is1 = new ByteArrayInputStream(dto.getGuideIdRearImage());
            BufferedImage bi1 = ImageIO.read(is1);
            File outputfile = new File("images/guide/guide_id_rear/" + dt + ".jpg");
            ImageIO.write(bi1, "jpg", outputfile);
            guide.setGuideIdRearImage(outputfile.getAbsolutePath());
        }

        if (dto.getGuideNicRearImage() != null) {
            InputStream is2 = new ByteArrayInputStream(dto.getGuideNicRearImage());
            BufferedImage bi2 = ImageIO.read(is2);
            File outputfile = new File("images/guide/nic_front/" + dt + ".jpg");
            ImageIO.write(bi2, "jpg", outputfile);
            guide.setGuideNicRearImage(outputfile.getAbsolutePath());
        }

        if (dto.getGuideNicFrontImag() != null) {
            InputStream is2 = new ByteArrayInputStream(dto.getGuideNicFrontImag());
            BufferedImage bi2 = ImageIO.read(is2);
            File outputfile = new File("images/guide/nic_rear/" + dt + ".jpg");
            ImageIO.write(bi2, "jpg", outputfile);
            guide.setGuideNicFrontImag(outputfile.getAbsolutePath());
        }

        if (dto.getGuideProfilePicImage() != null) {
            InputStream is2 = new ByteArrayInputStream(dto.getGuideProfilePicImage());
            BufferedImage bi2 = ImageIO.read(is2);
            File outputfile = new File("images/guide/guide_pro_pic/" + dt + ".jpg");
            ImageIO.write(bi2, "jpg", outputfile);
            guide.setGuideProfilePicImage(outputfile.getAbsolutePath());
        }

    }

    public void importImages(ResponseGuideDto dto, Guide guide) throws IOException {

        BufferedImage read = ImageIO.read(new File(guide.getGuideProfilePicImage()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        byte[] bytes = baos.toByteArray();
       // dto.setG(Base64.getEncoder().encodeToString(bytes));
        dto.setGuideProfilePicImage(bytes);


       read = ImageIO.read(new File(guide.getGuideNicFrontImag()));
        baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        bytes = baos.toByteArray();
       // dto.setNicFrontImg(Base64.getEncoder().encodeToString(bytes));
        dto.setGuideNicFrontImag(bytes);

       read = ImageIO.read(new File(guide.getGuideNicRearImage()));
        baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        bytes = baos.toByteArray();
       // dto.setNicRearImg(Base64.getEncoder().encodeToString(bytes));
        dto.setGuideNicRearImage(bytes);

        read = ImageIO.read(new File(guide.getGuideIdFrontImage()));
        baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        bytes = baos.toByteArray();
        // dto.setNicRearImg(Base64.getEncoder().encodeToString(bytes));
        dto.setGuideIdFrontImage(bytes);


        read = ImageIO.read(new File(guide.getGuideIdRearImage()));
        baos = new ByteArrayOutputStream();
        ImageIO.write(read, "jpg", baos);
        bytes = baos.toByteArray();
        // dto.setNicRearImg(Base64.getEncoder().encodeToString(bytes));
        dto.setGuideIdRearImage(bytes);
    }

    private void deleteImages(GuideDto guideDto, Guide guide) {
        if (guideDto.getGuideProfilePicImage()!=null){
            System.out.println("not null");
            boolean delete = new File(guide.getGuideProfilePicImage()).delete();
        }
        if (guideDto.getGuideNicFrontImag()!=null){
            System.out.println("not null");
            boolean delete = new File(guide.getGuideNicFrontImag()).delete();
        }
        if (guideDto.getGuideNicRearImage()!=null){
            System.out.println("not null");
            boolean delete = new File(guide.getGuideNicRearImage()).delete();
        }

        if (guideDto.getGuideIdFrontImage()!=null){
            System.out.println("not null");
            boolean delete = new File(guide.getGuideIdFrontImage()).delete();
        }
        if (guideDto.getGuideIdRearImage()!=null){
            System.out.println("not null");
            boolean delete = new File(guide.getGuideIdRearImage()).delete();
        }
    }

}


