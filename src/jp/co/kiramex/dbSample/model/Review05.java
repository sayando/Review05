package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        // 3. データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement spstmt = null;    // 更新前、更新後の検索用プリペアードステートメントオブジェクト
        PreparedStatement ipstmt = null;    // 更新処理用プリペアードステートメントオブジェクト
        ResultSet rs = null;

        try {
            // 1. ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "password");

            // 4. DBとやりとりする窓口（PreparedStatementオブジェクト）の作成
            // 検索用SQLおよび検索用PreparedStatementオブジェクトを取得
            String selectSql = "SELECT * FROM person where id = ?";
            spstmt = con.prepareStatement(selectSql);
           // 更新するCountryCodeを入力
            System.out.print("検索キーワードを入力してください > ");
            // 入力されたCountryCodeをPreparedStatementオブジェクトにセット
            int input = keyIn();

            spstmt.setInt(1, input);
            // 5, 6. Select文の実行と結果を格納／代入
            rs = spstmt.executeQuery();
            while (rs.next()) {
                // Name列の値を取得
                String name = rs.getString("name");
                // Age列の値を取得
                String age = rs.getString("age");
                // 取得した値を表示
                System.out.println(name + "\t" + age );
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
            // 8. 接続を閉じる
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (ipstmt != null) {
                try {
                    ipstmt.close();
                } catch (SQLException e) {
                    System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (spstmt != null) {
                try {
                    spstmt.close();
                } catch (SQLException e) {
                    System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
        }
    }
 /*
 * キーボードから入力された値をStringで返す 引数：なし 戻り値：入力された文字列
 */
private static int keyIn() {
    int line = 0;
    try {
        BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
        line = Integer.parseInt(key.readLine());
    } catch (IOException e) {

    }
    return line;
}
}
