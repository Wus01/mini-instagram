package instagram.miniinstagram.repository;

import instagram.miniinstagram.domain.Feed;
import instagram.miniinstagram.domain.ImageFile;
import instagram.miniinstagram.domain.User;
import instagram.miniinstagram.service.FeedService;
import instagram.miniinstagram.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

public class JdbcFeedRepository implements FeedRepository{
    private DataSource dataSource;

    @Autowired
    public JdbcFeedRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Feed saveContent(Feed feed) {
        String sql = "insert into post(memberid, content) values(?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);



            pstmt.setLong (1, feed.getMemberId());      //받아온 값의 1번
            pstmt.setString (2, feed.getContent());     //받아온 값의 2번

            pstmt.executeUpdate();                      //쿼리 전달(새로 등록할 때)

            rs = pstmt.getGeneratedKeys();


            if(rs.next()){
                feed.setId(rs.getLong(1));


            }else {
                throw new SQLException("id 조회 실패");

            }
            return feed;

        }catch (Exception e){
            throw new IllegalStateException(e);


        }finally {
            close(conn, pstmt, rs);
        }

    }

    @Override
    public Long saveImage(List<ImageFile> imageFiles, Long feedId) {
        String sql = "insert into image(feedid, originalname, storefilename, insertdate) values(?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        LocalDateTime now = LocalDateTime.now();
        //Date date = (Date) Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            for (ImageFile imageFile : imageFiles) {
                pstmt.setLong (1, feedId);      //받아온 값의 1번 = 게시글 id
                pstmt.setString (2, imageFile.getOriginalName());      //받아온 값의 2번 = 원래 이미지 이름
                pstmt.setString (3, imageFile.getStoreFileName());      //받아온 값의 3번 = 생성된 이미지 이름
                pstmt.setString (4,  now.toString());      //받아온 값의 4번 = 생성된 이미지 이름

                pstmt.executeUpdate();                      //쿼리 전달(새로 등록할 때)
                rs = pstmt.getGeneratedKeys();              //저장된 내용들만 가져옴

                if(rs.next()){
                    imageFile.setId(rs.getLong("imgid"));      //imgid setting 하는


                }else {
                    throw new SQLException("id 조회 실패");

                }
            }

            return feedId;

        }catch (Exception e){
            throw new IllegalStateException(e);


        }finally {
            close(conn, pstmt, rs);
        }

    }

    @Override
    public Optional<Feed> findById(Long id) {
        return Optional.empty();
    }
    private Connection getConnection()  {
        return DataSourceUtils.getConnection(dataSource);

    }

    private void close (Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(conn != null){
                close(conn);
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException{
        DataSourceUtils.releaseConnection(conn, dataSource);
    }


}
