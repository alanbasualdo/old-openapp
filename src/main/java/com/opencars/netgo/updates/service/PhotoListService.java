package com.opencars.netgo.updates.service;

import com.opencars.netgo.updates.entity.Photo;
import com.opencars.netgo.updates.entity.PhotoList;
import com.opencars.netgo.updates.repository.PhotoListRepository;
import com.opencars.netgo.updates.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhotoListService {

    @Autowired
    PhotoListRepository photoListRepository;

    @Autowired
    PhotoRepository photoRepository;

    public Page<PhotoList> getAllUpdates(Pageable pageable){
        return photoListRepository.findAllByOrderByIdDesc(pageable);
    }

    public Optional<PhotoList> getOne(Long id){
        return photoListRepository.findById(id);
    }

    @Transactional
    public long savePhotoListWithPhotos(PhotoList photoList) {

        List<Photo> listToSave = photoList.getPhotos().stream().map(
                photo -> photoRepository.save(photo)
        ).collect(Collectors.toList());

        PhotoList photoListSaved = new PhotoList();
        photoListSaved.setPhotos(listToSave);
        photoListSaved.setTitle(photoList.getTitle());

        photoListRepository.save(photoListSaved);

        return photoListSaved.getId();
    }

    public void save(PhotoList photoList){
        photoListRepository.save(photoList);
    }

    public int countUpdatesByDate(){

        LocalDate currentDate = LocalDate.now();

        int count = photoListRepository.countUpdatesByDate(currentDate);

        return count;

    }

}
