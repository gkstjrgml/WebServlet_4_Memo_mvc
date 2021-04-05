package kr.or.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.bit.dto.memo;
import kr.or.bit.utils.SingletonHelper;

/*
 1. db 연결
 2.CRUD 함수 생성(1개의 테이블에 대해서)>> memo 테이블
 2.1 전체조회 : select id, email , content from memo
 2.2 조건조회 : select id, email , content from memo where id = ? (id >> pk)
 2.3 삽입    : insert into memo(id,email,content) values(?,?,?)
 2.4 수정		: update memo set email=? , content=? where id =?
 2.5 삭제		: delete from memo where id =?
 2.6Like (검색)+검색(알파)	: where email like '%naver@%'
 
 ArrayList, HashMap 복습해야함
 */

public class memodao {
	Connection conn = null;
	
	public memodao() {
		conn = SingletonHelper.getConnection("oracle"); // singleton
	}
	
	//전체조회
	public List<memo> getMemoList() throws SQLException{
		
		PreparedStatement pstmt = null;
		String sql="select id, email,content from memo";
		
		pstmt = conn.prepareStatement(sql);
		ResultSet rs= pstmt.executeQuery();
		
		List<memo> memolist = new ArrayList<memo>(); //<POINT>
		//[new memo()][new memo()][new memo()][new memo()]
		while(rs.next()) {
			memo m = new memo();
			m.setId(rs.getString("id"));
			m.setEmail(rs.getString("email"));
			m.setContent(rs.getString("content"));
			
			memolist.add(m);
		}
		SingletonHelper.close(rs);
		SingletonHelper.close(pstmt);
		
		
		return memolist;
	}
	
	//조건조회(where id = ? : 데이터 1건 보장 : id컬럼 > unique, primary key)
	public memo getMemoListById(String id) {
		
		//select id, email , content from memo where id = ?
		//memo m = new memo();
		//return m
		
		return null;
	}
	
	//삽입
	//public int insertMemo(String id, String email, String content) parameter 객체
	public int insertMemo(memo m) {
		int resultrow =0;
		
		PreparedStatement pstmt = null;
		String sql = "insert into memo(id,email,content) values(?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getId());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getContent());
			
			resultrow = pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			SingletonHelper.close(pstmt);
		}
		
		return resultrow;
	}
	
	//수정
	public int updateMemo(memo m) {
		return 0;
	}
	//삭제
	public int deleteMemo(String id) {
		return 0;
	}
	
	//검색
	public memo idSearchByEmail(String email) {
		return null;
	}
	
	//ID유무 함수
	public String isCheckById(String id) {
		
		String ismemoid=null;
		ResultSet rs = null;	
		PreparedStatement pstmt = null;
		
		String sql="select id from memo where id=?";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			
			 rs= pstmt.executeQuery();
			 if(rs.next()) {
				 //같은 ID 존재
				 ismemoid ="false";
			 }else {
				 //사용가능한 ID
				 ismemoid = "true";
			 }
			 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			SingletonHelper.close(rs);
			SingletonHelper.close(pstmt);
		}
		
		return ismemoid;
	}
}
