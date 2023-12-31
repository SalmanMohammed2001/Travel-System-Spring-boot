package com.travelserviceapi.travelserviceapi.repo;

import com.travelserviceapi.travelserviceapi.embadded.Contact;
import com.travelserviceapi.travelserviceapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo  extends JpaRepository<User,String> {

    public boolean existsByUserEmail(String email);
    public User findByUserEmail(String email);


/*    @Modifying
    @Query(value = "UPDATE User u SET u.username = :username, u.UserPassword = :password, u.userAddress = :address, u.userDob = :dob, " +
            "u.userGender = :gender, u.userNic = :nic, u.userNicFrontImg = :nicFrontImg, u.userNicRearImg = :nicRearImg, " +
            "u.userProfilePic = :profilePic WHERE u.userEmail = :email")
    void updateUser(@Param("username") String username, @Param("password") String password, @Param("address") String address,
                    @Param("dob") String dob, @Param("gender") String gender, @Param("nic") String nic,
                    @Param("nicFrontImg") String nicFrontImg, @Param("nicRearImg") String nicRearImg,
                    @Param("profilePic") String profilePic, @Param("email") String email);
}*/

    @Modifying
    @Query(value = "UPDATE User u SET u.username = :username, u.UserPassword = :password, u.userAddress = :address, u.userDob = :dob, " +
            "u.userGender = :gender, u.userNic = :nic, u.userNicFrontImg = :nicFrontImg, u.userNicRearImg = :nicRearImg, " +
            "u.userProfilePic = :profilePic, u.userContact = :contact   WHERE u.userEmail = :email")
    public int updateUser(@Param("username") String username, @Param("password") String password, @Param("address") String address,
                               @Param("dob") String dob, @Param("gender") String gender, @Param("nic") String nic,
                               @Param("nicFrontImg") String nicFrontImg, @Param("nicRearImg") String nicRearImg,
                               @Param("profilePic") String profilePic , @Param("contact") Contact contact, @Param("email") String email);

    public void deleteUserByUserEmail(String email);
}
