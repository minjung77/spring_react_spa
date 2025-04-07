package com.example.zzyzzy.semiprojectv2.service;

import com.example.zzyzzy.semiprojectv2.domain.*;
import com.example.zzyzzy.semiprojectv2.repository.*;
import com.example.zzyzzy.semiprojectv2.utils.GalleryUploadService;
import com.example.zzyzzy.semiprojectv2.utils.PdsUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
@Slf4j
@Service
@RequiredArgsConstructor
public class PdsServiceImpl implements PdsService {

    private final PdsRepository pdsMapper;
    private final PdsAttachRepository pdsAttachMapper;
    private final PdsReplyRepository pdsReplyMapper;
    private final PdsUploadService pdsUploadService;

//    @Override
//    public List<GalleryListDTO> selectGallery() {
//        return galleryMapper.selectGallery();
//    }
//
//    @Transactional
//    @Override
//    public GalleryImageDTO readOneGalleryImage(String gno) {
//
//        galleryMapper.updateViewOne(gno);// 조회수 증가
//        GalleryViewDTO gal = galleryMapper.selectOneGallery(gno);// 본문 글 가져오기
//        List<GalleryImage> gi = galleryMapper.selectGalleryImages(gno);// 본문글에 포함된 이미지들 가져오기
//
//        return new GalleryImageDTO(gal, gi);
//    }

    @Transactional
    @Override
    public boolean newPds(Pds pds, List<MultipartFile> panames) {
        boolean result = false;
        // 작성한 게시글을 gallerys에 저장하고, 생성된 글 번호를 알아냄
        int pno = -999;
        try {
           Pds newOne = pdsMapper.save(pds);
           pno = newOne.getPno();
        }catch (Exception e){
            throw new IllegalStateException("insertGalley 오류 발생");
        }
        List<PdsAttach> pas = null;
        if(!panames.isEmpty()) {
            try{
                pas = pdsUploadService.processUpload(panames, pno);
            }catch (Exception e){
                throw new IllegalStateException("insertPds 오류 발생");
            }
            try {
                pdsAttachMapper.saveAll(pas);
            }catch (Exception e){
                throw new IllegalStateException("insertPdsAttach 오류 발생");
            }

            result = true;

        }

        return result;
    }

    @Override
    public PdsReplyDTO readOnePdsReply(int pno) {
        //pdsMapper.updateView(pno);//조회수 증가
        Pds pds = pdsMapper.findByPno(pno);
        List<PdsAttach> pas = pdsAttachMapper.findByPno(pno);
        List<PdsReply> prs = pdsReplyMapper.findByPno(pno);

        return new PdsReplyDTO(pds, pas, prs);
    }
}
