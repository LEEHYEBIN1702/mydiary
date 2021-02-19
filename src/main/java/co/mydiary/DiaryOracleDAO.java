package co.mydiary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DiaryOracleDAO implements DAO {
	
	   Connection conn;
	   Statement stmt;
	   PreparedStatement pstmt;
	   ResultSet rs;

	@Override
	public int insert(DiaryVO vo) {
		int r = 0;
		try {
			//1. connect (연결)
			conn = JdbcUtil.connect();
			//2. statement (구문)
			String sql = "insert into diary (wdate, contents ) "
					   + " values (?, ?) ";
			PreparedStatement pstmt = conn.prepareStatement(sql);	
			//3. execute (실행)
			pstmt.setString(1, vo.getWdate());
			pstmt.setString(2, vo.getContents());
			
			r = pstmt.executeUpdate();
			System.out.println(r + " 건이 등록됨");
			//4. resultset(select라면 조회결과 처리)
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//5. close(연결해제)
			JdbcUtil.disconnect(conn);
		}
		
		return r;
		
	  }
	
	@Override
	public void update(DiaryVO vo) {
		int r = 0;
		try {
			//1. connect (연결)
			conn = JdbcUtil.connect();
			//2. statement (구문)
			String sql = "UPDATE diary SET contents = ?  where wdate = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);	
			//3. execute (실행)
			pstmt.setString(1, vo.getWdate());
			pstmt.setString(2, vo.getContents());
			
			r = pstmt.executeUpdate();
			System.out.println(r + " 건이 등록됨");
			//4. resultset(select라면 조회결과 처리)
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			//5. close(연결해제)
			JdbcUtil.disconnect(conn);
		}
		
	}

	@Override
	public int delete(String date) {
		
		return 0;
	}

	@Override
	public DiaryVO selectDate(String date) {
	
		return null;
	}
    //select * from diary where contents like '%' || ? || '%'
	@Override
	public List<DiaryVO> selectContent(String content) {
		ArrayList<DiaryVO> list = new ArrayList<DiaryVO>();
		DiaryVO vo = null;
		try {
			conn = JdbcUtil.connect();
			String sql = "select  wdate,"
					   + " contents"
					   + " from diary"
					   + " where contents like '%' || ? || '%'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, content.trim());
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				vo = new DiaryVO();
				vo.setWdate(rs.getString("wdate"));
				vo.setContents(rs.getString("contents"));
				list.add(vo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.disconnect(conn);	
		}
		return list;	
	}


	@Override
	public List<DiaryVO> selectAll() {
		ArrayList<DiaryVO> list = new ArrayList<DiaryVO>();
		DiaryVO vo = null;
		try {
			conn = JdbcUtil.connect();
			String sql = "select  wdate,"
					   + " contents"
					   + " from diary"
					   + " order by wdate";
			pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				vo = new DiaryVO();
				vo.setWdate(rs.getString("wdate"));
				vo.setContents(rs.getString("contents"));
				list.add(vo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.disconnect(conn);
		}
		return list;	
	}

}
