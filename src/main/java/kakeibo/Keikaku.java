package kakeibo;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import szk.OraDbConnect;

/**
 * Servlet implementation class Keikaku
 */
@WebServlet("/Keikaku")
public class Keikaku extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Keikaku() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    static String userid = "000000";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		this.setRespHeader(response);
		PrintWriter out = null;
		
		out = this.getRespWriter(response);
		
		OraDbConnect oraConnect = new OraDbConnect("823surface", "XEPDB1", "kaihatu", "tsbtzkstshkr");
        if (oraConnect.OraConnect()) {
            System.out.println("接続失敗");
        }
        //ResultSet resultSet = oraConnect.ExcecuteQuery("SELECT * FROM TEST");
        //Vector<Vector<String>> vec = oraConnect.getSqlResult(resultSet);
        
        String setSql = "";
        setSql = " select アイテムID, i.カテゴリID, カテゴリ, アイテム, 金額 ";
        setSql = setSql + " from K_アイテムマスタ  i,K_カテゴリマスタ c ";
        setSql = setSql + " where i.ユーザID = c.ユーザID ";
        setSql = setSql + " and i.カテゴリID = c.カテゴリID ";
        setSql = setSql + " and i.ユーザID = '" + userid + "' ";
        setSql = setSql + " order by i.カテゴリID, アイテムID";
        
        ResultSet resultSet = oraConnect.ExcecuteQuery(setSql);
        Vector<Vector<String>> item = oraConnect.getSqlResult(resultSet);

        
		out.println("<html>");
		out.println("<head>");
		out.println("<title>main</title>");
		out.println("</head>");
		out.println("<body>");
		//out.println("<script type='text/javascript' src='/js/keikaku.js'></script>");
		out.println("<h1>HelloWorld</h1>");
		//out.println("<p id='test'>aiueo</p>");
		
		//out.println(item);
		
		String shocate ="";
		for (int i = 0; i < item.size(); i++) {
			Vector<String> itemRow = (Vector<String>)item.get(i);
			if (!shocate.equals(itemRow.get(2).toString().trim())) {
				if (!shocate.equals("")) {
					out.println("</table>");
				}
				out.println("<table id='itemTable_" + itemRow.get(1).toString().trim() + "'>");
				out.println("<tr id='itemRowTitle''><td>アイテムID</td><td>アイテム</td><td>金額</td></tr>");
				out.println("<tr class='itemRowSum'><td class='itemSumName'>" + itemRow.get(2).toString().trim() + "</td colspan='4'><td>0</td></tr>");
			} 
			out.println("<tr class='itemRowDetail'>");
			/*for (int j = 0; j < itemRow.size(); j++) {
				out.println("<td>" + itemRow.get(j).toString().trim() + "</td>");
			}*/
			out.println("<td class='itemNo' id='itemNo_" + itemRow.get(0).toString().trim() + "'>" + itemRow.get(0).toString().trim() + "</td><td class='itemNo' id='item_" + itemRow.get(0).toString().trim() + "'>"+ itemRow.get(3).toString().trim() + "</td><td class='itemNo' id='item_" + itemRow.get(0).toString().trim() + "'>"+ itemRow.get(4).toString().trim() + "</td>");
			out.println("</tr>");
			shocate = itemRow.get(2).toString().trim();
		}
		
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void setRespHeader(HttpServletResponse response) {
		// TODO 自動生成されたメソッド・スタブ
		//文字コードの設定
		response.setContentType("text/html; charset=UTF-8");
		//キャッシュの設定
		response.setHeader("Cache-Control", "no-cache,no-store");
		response.setHeader("Pragma", "no-cache");
	}
	
	PrintWriter getRespWriter(HttpServletResponse response) {
		//変数定義
		String encoding ="";
		OutputStreamWriter osw = null;
		
		//HTMLライタの設定
		encoding = response.getCharacterEncoding();
		try {
			osw = new OutputStreamWriter(response.getOutputStream(), encoding);
		} catch (Exception ex) {
			return null;
		}
		
		return new PrintWriter(osw, true);
	}


}
