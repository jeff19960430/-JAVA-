package com.Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.metadata.IIOInvalidTreeException;

import com.mysql.jdbc.Statement;




public class Operate {
  
   //添加比赛数据
   public boolean add_match(Match ee) throws IOException{
       String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//数据库账户，一般为root
       String password = "123456";//数据库密码
       Connection conn=null;
       PreparedStatement pstmt=null;
       try{
           //加载驱动程序
           Class.forName("com.mysql.jdbc.Driver");
           //获得数据库连接
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql="insert into match_information (uuid,theme,time,week,address,rule, directions,people,remarks,sponsor,sponsor_openid,user_join,user_leave,longitude,latitude)"
           		+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
           pstmt=conn.prepareStatement(sql);
           pstmt.setString(1, ee.Uuid);
           pstmt.setString(2, ee.Theme);
           pstmt.setString(3, ee.Time);
           pstmt.setString(4, ee.Week);
           pstmt.setString(5, ee.Address);
           pstmt.setString(6, ee.Rule);
           pstmt.setString(7, ee.Directions);
           pstmt.setString(8, ee.People);
           pstmt.setString(9, ee.Remarks);
           pstmt.setString(10, ee.Sponsor);
           pstmt.setString(11,ee.Sponsor_openid);
           pstmt.setString(12, ee.User_join);
           pstmt.setString(13, ee.User_leave);
           pstmt.setDouble(14,ee.Longitude);
           pstmt.setDouble(15, ee.Latitude);
           pstmt.execute();
             }catch(ClassNotFoundException e){
                 e.printStackTrace();
             }catch(SQLException e){
                 e.printStackTrace();
             }     
     
       return true;

  }
   
   //按照用户表中的比赛字段来查询对应ID的比赛
   public  ResultSet inquire_match(String user_match,String openid) throws ClassNotFoundException, SQLException {
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//数据库账户，一般为root
       String password = "123456";//数据库密码
       Connection conn=null;
       Statement stmt=null;
       Statement stmt1=null;
           //加载驱动程序
           Class.forName("com.mysql.jdbc.Driver");
           //获得数据库连接
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql1="UPDATE  match_status  SET match_status='3' WHERE STR_TO_DATE(match_time,'%Y-%m-%d %H:%i:%s') < NOW()";
           String sql="SELECT * FROM (SELECT * FROM  match_information WHERE FIND_IN_SET(id,'"+user_match+"')) AS a LEFT JOIN (SELECT * FROM match_status WHERE openid='"+openid+"') AS b ON a.uuid=b.status_uuid order by a.id desc";    
           stmt=(Statement) conn.createStatement();
           stmt.executeUpdate(sql1);
           ResultSet rs=stmt.executeQuery(sql);
           return  rs;
           
           
   }
   
   //插入用户信息数据
   public boolean add_user(User aa) throws IOException{
       String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//数据库账户，一般为root
       String password = "123456";//数据库密码
       Connection conn=null;
       PreparedStatement pstmt=null;
       try{
           //加载驱动程序
           Class.forName("com.mysql.jdbc.Driver");
           //获得数据库连接
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql="insert into user(openid,user_name,user_url,`match`)values(?,?,?,?)";
           pstmt=conn.prepareStatement(sql);
           pstmt.setString(1, aa.Openid);
           pstmt.setString(2, aa.User_name);
           pstmt.setString(3, aa.User_url);
           pstmt.setString(4, aa.Match);
           pstmt.execute();
             }catch(ClassNotFoundException e){
                 e.printStackTrace();
             }catch(SQLException e){
                 e.printStackTrace();
             }  
 
      
       return true;
  }
   
   //根据openid，取出该用户的名称，以此判断获取到的Openid是否在数据库已经存在
   public  ResultSet inquire_openid(String openid) throws ClassNotFoundException, SQLException {
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//数据库账户，一般为root
       String password = "123456";//数据库密码
       Connection conn=null;
       Statement stmt=null;
       
           //加载驱动程序
           Class.forName("com.mysql.jdbc.Driver");
           //获得数据库连接
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql="select user_name from user where openid = '"+openid+"'";
           stmt=(Statement) conn.createStatement();
           ResultSet rs=stmt.executeQuery(sql);
           return  rs;    

           
   }
   
   //更新该openid的用户名字和头像
   public  int update_user(User bb) throws ClassNotFoundException, SQLException {
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//数据库账户，一般为root
       String password = "123456";//数据库密码
       Connection conn=null;
       Statement stmt=null;    
           //加载驱动程序
           Class.forName("com.mysql.jdbc.Driver");
           //获得数据库连接
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql="update user set user_name='"+bb.User_name+"',user_url='"+bb.User_url+"'  where openid='"+bb.Openid+"'";
           stmt=(Statement) conn.createStatement();
           int result=stmt.executeUpdate(sql);
           return  result;
           
   }
   
   //根据openid找到用户的信息
   public  ResultSet find_userId(String openid) throws ClassNotFoundException, SQLException {
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//数据库账户，一般为root
       String password = "123456";//数据库密码
       Connection conn=null;
       Statement stmt=null;
       
           //加载驱动程序
           Class.forName("com.mysql.jdbc.Driver");
           //获得数据库连接
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql="select * from user where openid='"+openid+"'";
           stmt=(Statement) conn.createStatement();
           ResultSet userId=stmt.executeQuery(sql);
           return  userId;
      
   }
   
    //根据uuid取出比赛表中的所有信息。
   public  ResultSet find_matchInformation(String uuid) throws ClassNotFoundException, SQLException {
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//数据库账户，一般为root
       String password = "123456";//数据库密码
       Connection conn=null;
       Statement stmt=null;
           //加载驱动程序
           Class.forName("com.mysql.jdbc.Driver");
           //获得数据库连接
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql="select * from match_information where uuid='"+uuid+"'";
           stmt=(Statement) conn.createStatement();
           ResultSet allUser=stmt.executeQuery(sql);
           return  allUser;
      
   }
   //根据uuid和openid将比赛表和比赛转态表拼接起来。
  public  ResultSet find_matchStatus(String uuid,String openid) throws ClassNotFoundException, SQLException {
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
      String username = "root";//数据库账户，一般为root
      String password = "123456";//数据库密码
      Connection conn=null;
      Statement stmt=null;
          //加载驱动程序
          Class.forName("com.mysql.jdbc.Driver");
          //获得数据库连接
          conn=(Connection)DriverManager.getConnection(url,username,password);
          String sql="SELECT * FROM (select * from match_information where uuid='"+uuid+"') AS a LEFT JOIN (SELECT * FROM match_status WHERE openid='"+openid+"') AS b ON a.uuid=b.status_uuid";
          stmt=(Statement) conn.createStatement();
          ResultSet allUser=stmt.executeQuery(sql);
          return  allUser;
     
  }
   
      //将这场比赛报名字段和请假字段中中，新得到的字符串替换原来的字符串
    public  int update_join(String uuid,String rs_join,String rs_leave) throws ClassNotFoundException, SQLException { //传入两个参数，一个是比赛的openid,找到这场比赛，一个是新的报名字段字符串
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
       String username = "root";//数据库账户，一般为root
       String password = "123456";//数据库密码
       Connection conn=null;
       Statement stmt=null;    
           //加载驱动程序
           Class.forName("com.mysql.jdbc.Driver");
           //获得数据库连接
           conn=(Connection)DriverManager.getConnection(url,username,password);
           String sql="update match_information set user_join='"+rs_join+"',user_leave='"+rs_leave+"' where uuid='"+uuid+"'";
           stmt=(Statement) conn.createStatement();
           int result=stmt.executeUpdate(sql);
           return  result;
           
   }
    
    //从match比赛中，取出报名人的姓名和头像和点击时间
    public ResultSet register_information (String user_join,String uuid) throws ClassNotFoundException, SQLException { //传入两个参数，一个是比赛的openid,找到这场比赛，一个是新的报名字段字符串
		   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
	       String username = "root";//数据库账户，一般为root
	       String password = "123456";//数据库密码
	       Connection conn=null;
	       Statement stmt=null;
	           //加载驱动程序
	           Class.forName("com.mysql.jdbc.Driver");
	           //获得数据库连接
	           conn=(Connection)DriverManager.getConnection(url,username,password);
	           String sql="select user_name,user_url,click_time from (select * from user where FIND_IN_SET (id,'"+user_join+"')) AS a LEFT JOIN(SELECT * FROM match_status where status_uuid='"+uuid+"') AS b on a.openid=b.openid ORDER BY click_time";
	           stmt=(Statement) conn.createStatement();
	           ResultSet register_imformation=stmt.executeQuery(sql);      
	           return register_imformation;
	           
	   }

    
    //从match比赛中，取出请假人的姓名和 头像
    public ResultSet leave_information (String user_leave,String uuid) throws ClassNotFoundException, SQLException { //传入两个参数，一个是比赛的openid,找到这场比赛，一个是新的报名字段字符串
		   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
	       String username = "root";//数据库账户，一般为root
	       String password = "123456";//数据库密码
	       Connection conn=null;
	       Statement stmt=null;
	           //加载驱动程序
	           Class.forName("com.mysql.jdbc.Driver");
	           //获得数据库连接
	           conn=(Connection)DriverManager.getConnection(url,username,password);
	           String sql="select user_name,user_url,click_time from (select * from user where FIND_IN_SET (id,'"+user_leave+"')) AS a LEFT JOIN(SELECT * FROM match_status where status_uuid='"+uuid+"') AS b on a.openid=b.openid ORDER BY click_time";
	           stmt=(Statement) conn.createStatement();
	           ResultSet leave_imformation=stmt.executeQuery(sql);      
	           return leave_imformation;
	           
	   }

    //将用户表中的比赛字段进行更新
    public  int update_userMatch(String new_userMatch,String openid) throws ClassNotFoundException, SQLException { //传入两个参数，一个是新的比赛字段,一个是用户的id
	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
     String username = "root";//数据库账户，一般为root
     String password = "123456";//数据库密码
     Connection conn=null;
     Statement stmt=null;    
         //加载驱动程序
         Class.forName("com.mysql.jdbc.Driver");
         //获得数据库连接
         conn=(Connection)DriverManager.getConnection(url,username,password);
         String sql="update user set `match`='"+new_userMatch+"' where openid ='"+openid+"' ";
         stmt=(Statement) conn.createStatement();
         int result=stmt.executeUpdate(sql);
         return  result;
         
 }

    //在match_status中插入比赛状态和比赛时间
    public boolean add_matchStatus(String uuid,String openid,String status,String time,String click_time) throws IOException{
        String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
        String username = "root";//数据库账户，一般为root
        String password = "123456";//数据库密码
        Connection conn=null;
        PreparedStatement pstmt=null;
        try{
            //加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //获得数据库连接
            conn=(Connection)DriverManager.getConnection(url,username,password);
            String sql="insert into match_status(status_uuid,openid,match_status,match_time,click_time)values(?,?,?,?,?)";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, uuid);
            pstmt.setString(2, openid);
            pstmt.setString(3, status);
            pstmt.setString(4, time);
            pstmt.setString(5, click_time);
            pstmt.execute();
              }catch(ClassNotFoundException e){
                  e.printStackTrace();
              }catch(SQLException e){
                  e.printStackTrace();
              }  
  
       
        return true;
   }

    //在match_status中修改比赛状态和比赛时间
    public int edit_matchStatus(String uuid,String openid,String time) throws ClassNotFoundException, SQLException{
        String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
        String username = "root";//数据库账户，一般为root
        String password = "123456";//数据库密码
        Connection conn=null;
        Statement stmt=null;
     
            //加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //获得数据库连接
            conn=(Connection)DriverManager.getConnection(url,username,password);
            String sql="update match_status set match_time='"+time+"' where status_uuid='"+uuid+"' and openid='"+openid+"'";
            stmt=(Statement) conn.createStatement();
            int result=stmt.executeUpdate(sql);
            return  result;
   }
  
   //更新match_status中比赛对应用户的状态
    public  int update_matchStatus(String uuid,String openid,String status,String click_time) throws ClassNotFoundException, SQLException {
 	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
        String username = "root";//数据库账户，一般为root
        String password = "123456";//数据库密码
        Connection conn=null;
        Statement stmt=null;    
            //加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //获得数据库连接
            conn=(Connection)DriverManager.getConnection(url,username,password);
            String sql="update match_status set match_status='"+status+"' ,click_time='"+click_time+"' where openid='"+openid+"'and status_uuid='"+uuid+"'";
            stmt=(Statement) conn.createStatement();
            int result=stmt.executeUpdate(sql);
            return  result;
            
    }

    //从match_status中 取出对应状态
    public ResultSet inquire_matchStatus (String uuid,String openid) throws ClassNotFoundException, SQLException { //传入两个参数，一个是比赛的openid,找到这场比赛，一个是新的报名字段字符串
		   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
	       String username = "root";//数据库账户，一般为root
	       String password = "123456";//数据库密码
	       Connection conn=null;
	       Statement stmt=null;
	           //加载驱动程序
	           Class.forName("com.mysql.jdbc.Driver");
	           //获得数据库连接
	           conn=(Connection)DriverManager.getConnection(url,username,password);
	           String sql="select match_status from match_status where status_uuid='"+uuid+"' and openid='"+openid+"'";
	           stmt=(Statement) conn.createStatement();
	           ResultSet leave_imformation=stmt.executeQuery(sql);      
	           return leave_imformation;
	           
	   }
     
  //修改match中对应uuid的比赛信息
    public  int edit_match(String theme,String time,String week,String address,Double longitude,Double latitude,String rule,String directions,String remarks,String uuid) throws ClassNotFoundException, SQLException {
 	   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
        String username = "root";//数据库账户，一般为root
        String password = "123456";//数据库密码
        Connection conn=null;
        Statement stmt=null;    
            //加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //获得数据库连接
            conn=(Connection)DriverManager.getConnection(url,username,password);
            String sql="update match_information set theme='"+theme+"',time='"+time+"',week='"+week+"',address='"+address+"',"
            		+ "longitude='"+longitude+"',latitude='"+latitude+"',rule='"+rule+"',directions='"+directions+"',"
            				+ "remarks='"+remarks+"' where uuid='"+uuid+"'";
            stmt=(Statement) conn.createStatement();
            int result=stmt.executeUpdate(sql);
            return  result;
            
    }
 
    //从match_information表和match_status表中删除对应数据
    public Boolean delete_match(String uuid,String openid) throws ClassNotFoundException, SQLException { //传入两个参数，一个是比赛的openid,找到这场比赛，一个是新的报名字段字符串
		   String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
	       String username = "root";//数据库账户，一般为root
	       String password = "123456";//数据库密码
	       Connection conn=null;
	       Statement stmt=null;
	           //加载驱动程序
	           Class.forName("com.mysql.jdbc.Driver");
	           //获得数据库连接
	           conn=(Connection)DriverManager.getConnection(url,username,password);
	           String sql_information="delete from match_information where uuid='"+uuid+"'";
	           String sql_status="delete from match_status where status_uuid='"+uuid+"' and openid='"+openid+"'";
	           stmt=(Statement) conn.createStatement();
	           int update1=stmt.executeUpdate(sql_information);
	           int update2=stmt.executeUpdate(sql_status);
	           return true;
	           
	   }
   
    //在chlick_time表中插入报名或请假的点击时间
  
  public boolean add_clickTime(String clickTime_uuid,String clickTime_openid,String clickTime_time,String clickTime_status) throws IOException{
        String url = "jdbc:mysql://localhost:3306/match?useSSL=false";
        String username = "root";//数据库账户，一般为root
        String password = "123456";//数据库密码
        Connection conn=null;
        PreparedStatement pstmt=null;
        try{
            //加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //获得数据库连接
            conn=(Connection)DriverManager.getConnection(url,username,password);
            String sql="insert into click_time(clickTime_uuid,clickTime_openid,clickTime_time,clickTime_status)values(?,?,?,?)";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, clickTime_uuid);
            pstmt.setString(2, clickTime_openid);
            pstmt.setString(3, clickTime_time);
            pstmt.setString(3, clickTime_status);
            pstmt.execute();
              }catch(ClassNotFoundException e){
                  e.printStackTrace();
              }catch(SQLException e){
                  e.printStackTrace();
              }  
  
       
        return true;
   }
}


