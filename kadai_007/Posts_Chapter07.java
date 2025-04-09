package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {

	public static void main(String[] args) {

		//		フィールド宣言
		Connection con = null;
		PreparedStatement insertStatement = null;
		PreparedStatement searchStatement = null;

		//		追加するレコードの内容を用意

		String[][] postList = {
				{ "1003", "2023-02-28", "昨日の夜は徹夜でした・・", "13" },
				{ "1002", "2023-02-08", "お疲れ様です！", "12" },
				{ "1003", "2023-02-09", "今日も頑張ります！", "18" },
				{ "1001", "2023-02-09", "無理は禁物ですよ！", "17" },
				{ "1002", "2023-02-10", "明日から連休ですね！", "20" }
		};

		//		通信部分
		try {

			//		セッション情報を取得	
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"Hagesekken12");
			System.out.println("データベース接続成功 : " + con);

			//				レコード挿入部分
			//			テーブル追加用のSQLクエリの作成
			String InsertSql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?);";

			insertStatement = con.prepareStatement(InsertSql);

			//			SQLクエリの送信

			System.out.println("レコード追加を実行します");

			//			postListの各フィールドをデータベースのフィールドの型に合うように変換して代入
			int recordCount = 0;
			for (String[] post : postList) {
				insertStatement.setInt(1, Integer.parseInt(post[0]));
				insertStatement.setDate(2, java.sql.Date.valueOf(post[1]));
				insertStatement.setString(3, post[2]);
				insertStatement.setInt(4, Integer.parseInt(post[3]));

				//				ココで送信
				insertStatement.executeUpdate();
				recordCount++;

			}
			System.out.println(recordCount + "件のレコードが追加されました");

			//			レコード検索部分
			//			テーブル検索用のSQLクエリの作成
			String searchSql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002;";
			searchStatement = con.prepareStatement(searchSql);

			//			検索結果の入れ物用オブジェクトresultに検索SQLクエリの結果を入れる
			ResultSet result = searchStatement.executeQuery();

			System.out.println("ユーザーIDが1002のレコードを検索しました");
			
			//			SQLクエリの実行結果をresultから抽出
			while (result.next()) {
				Date postDate = result.getDate("posted_at");
				String postContent = result.getString("post_content");
				int likesCount = result.getInt("likes");

				System.out.println(
						result.getRow() + "件目：投稿日時=" + postDate + "／投稿内容=" + postContent + "／いいね数=" + likesCount);

			}

		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		}finally {
//			メモリの開放
//			先にstatement開放
			if( insertStatement != null) {
				try {
					insertStatement.close();
				}catch(SQLException ignore) {}
			}
			if( searchStatement != null) {
				try {
					searchStatement.close();
				}catch(SQLException ignore) {}
			}
//			次にコネクションの開放
			if( con != null ) {
				try {
					con.close();
				}catch(SQLException ignore){}
			}
			
			
		}
		

	}
}
