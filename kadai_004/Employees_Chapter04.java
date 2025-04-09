package kadai_004;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Employees_Chapter04 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

//		フィールド宣言
		Connection  connect = null;
		Statement statement = null;
		
		
//		メイン部分
		try {
			
//			接続してセッション情報を格納
			connect = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"Hagesekken12"					
					);
			
			
			System.out.println("データベース接続成功 : " + connect);
			
;			
//			connectセッション用のSQLクエリを作成

		statement = connect.createStatement();
//		テーブルを作成するSQL本文を用意
		String sql = """
					CREATE TABLE employees (
					id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
					name VARCHAR(60) NOT NULL,
					email VARCHAR(256) NOT NULL,
					age INT(11), 
					address VARCHAR(256)
					);
				
				""";
			
			int rowCnt = statement.executeUpdate(sql);
			System.out.println("社員テーブルを作成しました:更新レコード数=" + rowCnt);
			
		
			
		} catch(SQLException e) {
			System.out.println("エラー:" + e.getMessage());
			
		}finally {
//			メモリの開放
//			先にstatement開放
			if( statement != null) {
				try {
					statement.close();
				}catch(SQLException ignore) {}
			}
//			次にコネクションの開放
			if( connect != null ) {
				try {
					connect.close();
				}catch(SQLException ignore){}
			}
			
			
		}
		
		
		
		
		
	}

}
