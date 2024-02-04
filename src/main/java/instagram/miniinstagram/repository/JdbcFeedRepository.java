package instagram.miniinstagram.repository;

import instagram.miniinstagram.domain.Feed;
import instagram.miniinstagram.domain.User;
import instagram.miniinstagram.service.FeedService;
import instagram.miniinstagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class JdbcFeedRepository implements FeedRepository{
    private DataSource dataSource;

    @Autowired
    public JdbcFeedRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Feed saveContent(Feed feed) {
        String sql = "insert into post(content) values(?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString (1, feed.getContent());
/*
            pstmt.setString(5, user.getProfile_img());
            pstmt.setString(6, user.getWebSite());
            pstmt.setString(7, user.getIntroduction());
*/

            pstmt.executeUpdate();                      //쿼리 전달

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
