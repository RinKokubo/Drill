package servlet;

//自分が格納されているフォルダの外にある必要なクラス
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Kaitou;
import control.KaitouManager;
import control.RiyoushaManager;

//アノテーションの記述
@WebServlet("/UpdateKaitou")

// HttpServletを継承することで、このクラスはServletとして、働くことができる
public class UpdateKaitou extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// doPostメソッドから呼び出される(リダイレクトされる)
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// requestオブジェクトの文字エンコーディングの設定
		request.setCharacterEncoding("UTF-8");
		// forwardはrequestオブジェクトを引数として、次のページに渡すことができる
		RequestDispatcher dispatcher = request.getRequestDispatcher("/finish.jsp");
		dispatcher.forward(request, response);
	}

	// requestオブジェクトには、フォームで入力された文字列などが格納されている。
	// responseオブジェクトを使って、次のページを表示する
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// requestオブジェクトの文字エンコーディングの設定
		request.setCharacterEncoding("UTF-8");

		// requestオブジェクトから登録情報の取り出し
		String Id = request.getParameter("id");
		String Kaitou = request.getParameter("kaitou");
		String Pass = request.getParameter("pass");
		RiyoushaManager manager2 = new RiyoushaManager();
		String Password2 = manager2.SHA2(Pass).toString();

		Id = escape(Id);
		Kaitou = escape(Kaitou);
		Pass = escape(Pass);
		Password2 = escape(Password2);

		// コンソールに確認するために出力
		System.out.println("取得した文字列は" + Id + "です！");
		System.out.println("取得した文字列は" + Kaitou + "です！");
		System.out.println("取得した文字列は" + Pass + "です！");

		// kaitouオブジェクトに情報を格納
		Kaitou kaitou = new Kaitou(Id,0, "", "", "", Kaitou, Password2);

		// KaitouManagerオブジェクトの生成
		KaitouManager manager = new KaitouManager();

		// 更新
		manager.updateKaitou(kaitou);

		// 成功画面を表示する
		response.sendRedirect("/Drill/UpdateKaitou");
	}

	private static String escape(String val) {
		if (val == null) return "";
		val = val.replaceAll("&", "& amp;");
		val = val.replaceAll("<", "& lt;");
		val = val.replaceAll(">", "& gt;");
		val = val.replaceAll("\"", "&quot;");
		val = val.replaceAll("'", "&apos;");
		return val;
	  }
}
