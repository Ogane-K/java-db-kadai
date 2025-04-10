package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {

	public static void main(String[] args) {

		//		フィールドの宣言

		Connection dbConnection = null;

		PreparedStatement updateStatement = null;
		Statement selectOrderStatement = null;

		//	通信部分
		try {

			//			接続の確立

			dbConnection = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"Hagesekken12");

			System.out.println("データベース接続成功：" + dbConnection);

			//			データを更新する部分
			//			データ更新用のSQLクエリの作成

			String updateSql = "UPDATE scores SET score_math = ? , score_english = ? WHERE id = 5;";
			updateStatement = dbConnection.prepareStatement(updateSql);

			updateStatement.setInt(1, 95);
			updateStatement.setInt(2, 80);

			//			SQLクエリを送信して、レコードを更新する

			System.out.println("レコード更新を実行します");
			System.out.println(updateStatement.executeUpdate() + "件のレコードが更新されました");

			//			データを並べ替えて表示させる部分
			//			データ表示用のSQLクエリの作成

			String selectOrderSql = "SELECT * FROM scores ORDER BY score_math DESC , score_english DESC;";
			selectOrderStatement = dbConnection.createStatement();

			//			並び替えした状態のテーブルを取得して、リザルト変数に保存
			ResultSet selectResult = selectOrderStatement.executeQuery(selectOrderSql);
			System.out.println("数学・英語の点数が高い順に並べ替えました");

			//			リザルト変数の内容を結果として表示する
			while (selectResult.next()) {
				System.out.println(selectResult.getRow() + "件目：生徒ID="
						+ selectResult.getInt("id") + "／氏名="
						+ selectResult.getString("name") + "／数学="
						+ selectResult.getInt("score_math") + "／英語="
						+ selectResult.getInt("score_english"));
			}

		} catch (SQLException e) {

			System.out.println("エラーが発生しました：" + e.getMessage());

		} finally {
			//		更新用ステートメントのクローズ
			if (updateStatement != null) {

				try {
					updateStatement.close();
				} catch (SQLException ignore) {
				}

			}

			//		並べ替え表示用のステートメントのクローズ
			if (selectOrderStatement != null) {

				try {
					selectOrderStatement.close();
				} catch (SQLException ignore) {
				}

			}

			//		コネクション全体のクローズ
			if (dbConnection != null) {

				try {
					dbConnection.close();
				} catch (SQLException ignore) {
				}

			}

		}

	}

}
