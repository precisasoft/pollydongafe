package ec.com.vipsoft.ce.listener;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;

import ec.com.vipsoft.ce.backend.service.ContenedorReportesRide;

/**
 * Application Lifecycle Listener implementation class MyContextListener
 *
 */
@WebListener
public class MyContextListener extends org.apache.shiro.web.env.EnvironmentLoaderListener implements ServletContextListener {

	 private static final Logger LOG = Logger.getLogger(MyContextListener.class.getName());
	private ProcessEngine defaultProcessEngine;
    /**
     * Default constructor. 
     */
    public MyContextListener() {
    	super();
    }

    @EJB
    private ContenedorReportesRide contenedorRide;
	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         super.contextDestroyed(arg0);
         ProcessEngines.destroy();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         super.contextInitialized(arg0);
         defaultProcessEngine= ProcessEngines.getDefaultProcessEngine();        
         RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
         LOG.info("Number of process definitions :"+repositoryService.createProcessDefinitionQuery().count());
         LOG.info("<------------------------  PROCESOS DESPLEGADOS ---------------------->");
         List<ProcessDefinition> lista = repositoryService.createProcessDefinitionQuery().list();
         if(lista.isEmpty()){
        	 repositoryService.createDeployment().addClasspathResource("procesoEnvio.bpmn").deploy();
         }
 		for(ProcessDefinition d:lista){
 				LOG.info("deployment id "+d.getDeploymentId()+"   key  "+d.getKey());				
 		}
 		
 		LOG.info("<------------------------  FIN DE LISTA DE PROCESOS DESPLEGADOS ---------------------->");
 		
 		 
		try {
			JasperReport  jasperFactura = (JasperReport)JRLoader.loadObject(arg0.getServletContext().getResourceAsStream("ride_factura_1_1_0.jasper"));
			JasperReport  jasperFacturasinLogo=(JasperReport)JRLoader.loadObject(arg0.getServletContext().getResourceAsStream("ride_factura_1_1_0_nologo.jasper"));
			JasperReport  jasperretencion=(JasperReport)JRLoader.loadObject(arg0.getServletContext().getResourceAsStream("ride_retencion_1_0.jasper"));
			JasperReport  jasperretencionnologo=(JasperReport)JRLoader.loadObject(arg0.getServletContext().getResourceAsStream("ride_retencion_1_0_nologo.jasper"));
			JasperReport  jasperGuianologo=(JasperReport)JRLoader.loadObject(arg0.getServletContext().getResourceAsStream("ride_guiaremision_1_1_0_nologo.jasper"));
			JasperReport jasperGuia=(JasperReport)JRLoader.loadObject(arg0.getServletContext().getResourceAsStream("ride_guiaremision_1_1_0.jasper"));
			
			JasperReport jaspernc=(JasperReport)JRLoader.loadObject(arg0.getServletContext().getResourceAsStream("ride_nc_1_1_0.jasper"));
			JasperReport jasperncnologo=(JasperReport)JRLoader.loadObject(arg0.getServletContext().getResourceAsStream("ride_nc_1_1_0_nologo.jasper"));
			contenedorRide.setRideFactura(jasperFactura);
			contenedorRide.setRideFacturaSinLogo(jasperFacturasinLogo);
			contenedorRide.setRideRetencion(jasperretencion);
			contenedorRide.setRideRetencionSinLogo(jasperretencionnologo);
			contenedorRide.setRideGuiaRemision(jasperGuia);
			contenedorRide.setRideGuiaRemisionSinLogo(jasperGuianologo);
			contenedorRide.setRideNotaCredito(jaspernc);
			contenedorRide.setRideNotaCreditoSinLogo(jasperncnologo);
			
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
// 		
 		
    }
	
}
