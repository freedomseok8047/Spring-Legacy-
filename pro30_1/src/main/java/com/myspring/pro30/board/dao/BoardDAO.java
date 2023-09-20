package com.myspring.pro30.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.myspring.pro30.board.vo.ArticleVO;


public interface BoardDAO {
	public List selectAllArticlesList() throws DataAccessException;
	//답글 작성하기.
	public int insertReplyNewArticle(Map articleMap) throws DataAccessException;
	// 일반 데이터 글쓰기, 단일 이미지 사용하고, 다중 이미지도 사용중. 
	public int insertNewArticle(Map articleMap) throws DataAccessException;
	// 다중이미지 글쓰기- 여러 이미지들만 따로 디비에 저장하는 로직. 
	public void insertNewImage(Map articleMap) throws DataAccessException;
	
	public ArticleVO selectArticle(int articleNO) throws DataAccessException;
	public void updateArticle(Map articleMap) throws DataAccessException;
	public void deleteArticle(int articleNO) throws DataAccessException;
	public List selectImageFileList(int articleNO) throws DataAccessException;
	
}
