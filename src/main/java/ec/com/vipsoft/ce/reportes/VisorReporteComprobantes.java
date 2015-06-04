package ec.com.vipsoft.ce.reportes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class VisorReporteComprobantes
 */
@WebServlet("/VisorReporteComprobantes")
public class VisorReporteComprobantes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisorReporteComprobantes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String claveAcceso=request.getParameter("claveAcceso");
//			String fechaI=request.getParameter("fechaI");
//			String fechaF=request.getParameter("fechaF");
//		
//			byte[] datosPdf=creadorRide.obtenerPDF(claveAcceso);
//			response.setContentType ("application/pdf");
//			StringBuilder nombreArchivo=new StringBuilder("inline; filename=");
//			nombreArchivo.append("reporte_comprobantes");
//			nombreArchivo.append(".pdf");
//			response.setHeader ("Content-disposition",nombreArchivo.toString());
//			response.setHeader ("Cache-Control", "max-age=30");
//			response.setHeader ("Pragma", "No-cache");
//			response.setDateHeader ("Expires", 0);
//			response.setContentLength (datosPdf.length);
//			ServletOutputStream out;
//			out = response.getOutputStream ();
//
//			out.write (datosPdf, 0, datosPdf.length);
//			out.flush ();
//			out.close ();
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request,response);
	}

}
