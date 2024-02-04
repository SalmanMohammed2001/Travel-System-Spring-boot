package com.travelserviceapi.travelserviceapi.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.travelserviceapi.travelserviceapi.dto.core.HotelDto;
import com.travelserviceapi.travelserviceapi.dto.core.VehicleDto;
import com.travelserviceapi.travelserviceapi.dto.requestDto.RequestHotelDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseHotelDto;
import com.travelserviceapi.travelserviceapi.dto.responseDto.ResponseVehicleDto;
import com.travelserviceapi.travelserviceapi.entity.Hotel;
import com.travelserviceapi.travelserviceapi.entity.Vehicle;
import com.travelserviceapi.travelserviceapi.exception.DuplicateEntryException;
import com.travelserviceapi.travelserviceapi.exception.EntryNotFoundException;
import com.travelserviceapi.travelserviceapi.repo.HotelRepo;
import com.travelserviceapi.travelserviceapi.service.HotelService;
import com.travelserviceapi.travelserviceapi.util.Generator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    private final ModelMapper mapper;

    private final Generator generator;
    private final HotelRepo hotelRepo;

    private final Gson gson;

    public HotelServiceImpl(ModelMapper mapper, Generator generator, HotelRepo hotelRepo, Gson gson) {
        this.mapper = mapper;
        this.generator = generator;
        this.hotelRepo = hotelRepo;
        this.gson = gson;
    }

    @Override
    public void save(RequestHotelDto dto) throws IOException {
        String generateKey = generator.generateKey("Hotel");
        HotelDto hotelDto = mapper.map(dto, HotelDto.class);
        hotelDto.setHotelId(generateKey);
        if(!hotelRepo.existsById(hotelDto.getHotelId())){
            Hotel hotel = mapper.map(hotelDto, Hotel.class);
            exPortImage(hotelDto,hotel);
            hotelRepo.save(hotel);

        }else {
            throw new DuplicateEntryException("Duplicate Id");
        }
    }



    @Override
    public ResponseHotelDto findById(String id) {

        try {
            if(hotelRepo.existsById(id)){
                Hotel hotel = hotelRepo.findById(id).get();
                ResponseHotelDto responseHotelDto = mapper.map(hotel, ResponseHotelDto.class);
                importImages(responseHotelDto,hotel);
                return responseHotelDto;
            }else {
                throw new DuplicateEntryException("Id Not Found");
            }
        }catch (Exception e){
            throw new DuplicateEntryException("Id Not Found");
        }


    }

    @Override
    public void updateHotel(RequestHotelDto dto) throws IOException {
        HotelDto hotelDto = mapper.map(dto, HotelDto.class);
        if(hotelRepo.existsById(dto.getHotelId())){
            Hotel hotelImage = hotelRepo.findById(dto.getHotelId()).get();
            deleteImage(hotelImage);
            Hotel hotel = mapper.map(dto, Hotel.class);
            exPortImage(hotelDto,hotel);
            hotelRepo.save(hotel);
        }else {
            throw new EntryNotFoundException("Id Not Found");
        }
    }

    @Override
    public void deleteHotel(String id) {
        try {
            if(hotelRepo.existsById(id)){
                Hotel hotel = hotelRepo.findById(id).get();
                deleteImage(hotel);
                hotelRepo.deleteById(id);
            }else {
                throw new EntryNotFoundException("Id Not Found");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

}

    @Override
    public List<ResponseHotelDto> findAll() throws Exception {
        List<Hotel> all = hotelRepo.findAll();
        List<ResponseHotelDto> responseHotelDto = mapper.map(all, new TypeToken<List<ResponseHotelDto>>() {}.getType());
         importImagesAll(responseHotelDto, all);
         responseHotelDto.forEach(data->{
         System.out.println(data.getImages().toString());
            // System.out.println(data.getHotelId());
         });

         if(responseHotelDto.isEmpty()){
            throw new DuplicateEntryException("null");

         }
        return responseHotelDto;

    }

    @Override
    public List<ResponseHotelDto> findAllByHotelCategoryEquals(String category) throws Exception {
      List<Hotel> allByHotelCategoryEquals = hotelRepo.findAllByHotelCategoryEquals(category);
        List<ResponseHotelDto> responseHotelDto = mapper.map(allByHotelCategoryEquals, new TypeToken<List<ResponseHotelDto>>() {}.getType());
        importImagesAll(responseHotelDto, allByHotelCategoryEquals);
  return responseHotelDto;


    }

    public void deleteImage(Hotel hotel){
        if (hotel!=null){
            String images = hotel.getImages();
            if (images != null){
                ArrayList<String> pathList = gson.fromJson(images, new TypeToken<ArrayList<String>>(){}.getType());
                for (String path : pathList) {
                    File file = new File(path);
                    boolean delete = file.delete();
                    System.out.println("Images " + delete);
                }
            }
        }
    }

    public void exPortImage(HotelDto dto, Hotel hotel) throws IOException {
        ArrayList<byte[]> images = dto.getImages();
        String dt = LocalDate.now().toString().replace("-", "_") + "__"
                + LocalTime.now().toString().replace(":", "_");
        //     String dt = "img";

        ArrayList<String> pathList = new ArrayList<>();

        for (int i = 0; i < images.size(); i++) {
            {
                try {
                    InputStream is = new ByteArrayInputStream(images.get(i));
                    BufferedImage bi = ImageIO.read(is);
                    File outputfile = new File("images/hotel/" + dt + "_" + i + ".jpg");
                    ImageIO.write(bi, "jpg", outputfile);
                    pathList.add(outputfile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        hotel.setImages(gson.toJson(pathList));




    }

    public void importImages(ResponseHotelDto hotelDto, Hotel hotel) throws IOException {

        String images = hotel.getImages();
        hotelDto.setImages(new ArrayList<>());
        ArrayList<String> imageList = gson.fromJson(images, new TypeToken<ArrayList<String>>() {}.getType());
        for (int i = 0; i < imageList.size(); i++) {
            BufferedImage r = ImageIO.read(new File(imageList.get(i)));
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(r, "jpg", b);
            byte[] imgData= b.toByteArray();
            hotelDto.getImages().add(imgData);
        }


    }

/*    List<ResponseHotelDto> importImagesAll(List<ResponseHotelDto> hotelDto,List<Hotel> hotels) throws Exception {

        if(hotels ==null && hotelDto == null) return  null;
        Hotel hotel = new Hotel();

        ResponseHotelDto hotelDto1= new ResponseHotelDto();
        hotels.forEach(data->{
            hotel.setImages(data.getImages());

        });


        String images = hotel.getImages();

        ResponseHotelDto responseHotelDto = new ResponseHotelDto();
        responseHotelDto.setImages(new ArrayList<>());
        ArrayList<String> imageList = gson.fromJson(images, new TypeToken<ArrayList<String>>() {}.getType());
        for (int i = 0; i < imageList.size(); i++) {
            BufferedImage r = ImageIO.read(new File(imageList.get(i)));
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ImageIO.write(r, "jpg", b);
            byte[] imgData= b.toByteArray();
            responseHotelDto.getImages().add(imgData);
        };



        List<ResponseHotelDto> arrayList = new ArrayList<>();
        for (ResponseHotelDto data:hotelDto){

            arrayList.add(new ResponseHotelDto(
                    data.getHotelId(),data.getHotelName(),data.getHotelCategory(),data.getHotelPetAllowed(),
                    data.getHotelMapLink(),data.getHotelAddress(),data.getHotelContact(),data.getHotelEmail(),
                    data.getHotelPrices(),responseHotelDto.getImages(),hotelDto1.getHotelFrontImage(),data.isHotelStatus()
            ));
        }
        return arrayList;
    }*/
 public  void  importImagesAll(List<ResponseHotelDto> hotelDto,List<Hotel> hotels) throws Exception {

     if(hotels ==null && hotelDto == null) return ;
     Hotel hotel = new Hotel();

   //  ResponseHotelDto hotelDto1= new ResponseHotelDto();
     hotels.forEach(data->{
         hotel.setImages(data.getImages());
        // System.out.println(data.getImages());

        // String images = hotel.getImages();
       //  System.out.println(hotel.getImages());
        ArrayList<byte[]> imageByte=new ArrayList<>();
         ResponseHotelDto responseHotelDto = new ResponseHotelDto();
         responseHotelDto.setImages(new ArrayList<>());
         ArrayList<String> imageList = gson.fromJson(hotel.getImages(), new TypeToken<ArrayList<String>>() {}.getType());
         for (int i = 0; i < imageList.size(); i++) {
             BufferedImage r = null;
             try {
                 r = ImageIO.read(new File(imageList.get(i)));
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
             ByteArrayOutputStream b = new ByteArrayOutputStream();
             try {
                 ImageIO.write(r, "jpg", b);
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
             byte[] imgData= b.toByteArray();
             imageByte.add(imgData);
            System.out.println(imageByte);
             System.out.println("-----------");

             hotelDto.forEach(data1->{
                 data1.setImages(imageByte);
             });

         };

     });



 }

}
