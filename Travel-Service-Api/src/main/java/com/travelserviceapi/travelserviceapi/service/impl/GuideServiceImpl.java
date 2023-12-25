package com.travelserviceapi.travelserviceapi.service.impl;

import com.google.gson.Gson;
import com.travelserviceapi.travelserviceapi.dto.core.GuideDto;
import com.travelserviceapi.travelserviceapi.dto.core.UserDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestGuideDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseGuideDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseUserDto;
import com.travelserviceapi.travelserviceapi.entity.Guide;
import com.travelserviceapi.travelserviceapi.entity.User;
import com.travelserviceapi.travelserviceapi.exception.DuplicateEntryException;
import com.travelserviceapi.travelserviceapi.repo.GuideRepo;
import com.travelserviceapi.travelserviceapi.service.GuideService;
import com.travelserviceapi.travelserviceapi.util.Generator;
import org.modelmapper.ModelMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;

public class GuideServiceImpl implements GuideService {

    private final ModelMapper mapper;

    private final Generator generator;
    private final Gson gson;
    private final GuideRepo guideRepo;
    public GuideServiceImpl(ModelMapper mapper, Generator generator, Gson gson, GuideRepo guideRepo) {
        this.mapper = mapper;
        this.generator = generator;
        this.gson = gson;
        this.guideRepo = guideRepo;
    }

    @Override
    public ResponseGuideDto saveGuide(RequestGuideDto dto) throws IOException {
        GuideDto guideDto = mapper.map(dto, GuideDto.class);
        String generateKey = generator.generateKey("Guide");
        guideDto.setGuideId(generateKey);
        if(!guideRepo.existsById(guideDto.getGuideId())){
            Guide guide = mapper.map(guideDto, Guide.class);
            exportImages(guideDto,guide);
            guideRepo.save(guide);
        }else {
            throw new DuplicateEntryException("duplicate Guide Id");
        }
        return null;
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

        if (dto.getGuideIdFrontImage() != null) {
            InputStream is2 = new ByteArrayInputStream(dto.getGuideIdFrontImage());
            BufferedImage bi2 = ImageIO.read(is2);
            File outputfile = new File("images/guide/nic_rear/" + dt + ".jpg");
            ImageIO.write(bi2, "jpg", outputfile);
            guide.setGuideIdFrontImage(outputfile.getAbsolutePath());
        }

        if (dto.getGuideIdRearImage() != null) {
            InputStream is2 = new ByteArrayInputStream(dto.getGuideIdRearImage());
            BufferedImage bi2 = ImageIO.read(is2);
            File outputfile = new File("images/guide/nic_rear/" + dt + ".jpg");
            ImageIO.write(bi2, "jpg", outputfile);
            guide.setGuideIdRearImage(outputfile.getAbsolutePath());
        }

    }

    public void importImages(ResponseUserDto dto, User user) throws IOException {

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
}


