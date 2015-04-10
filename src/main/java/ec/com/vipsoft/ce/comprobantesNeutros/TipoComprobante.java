package ec.com.vipsoft.ce.comprobantesNeutros;

public enum TipoComprobante {
	factura{
		@Override public String toString(){return "01";}
	},Nc{
		@Override public String toString(){return "04";}
	},Nd{
		@Override public String toString(){return "05";}
	},Gr{
		@Override public String toString(){return "06";}
	},Cr{
		@Override public String toString(){return "07";}
	}
	
	
}
