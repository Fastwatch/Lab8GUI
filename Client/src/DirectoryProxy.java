public class DirectoryProxy implements Directory {
	
	@Override
	public void print() {
		Client.serverCall("{\"print\":\"\"}");
	}

	@Override
	public void add(String s) {
		Client.serverCall("{\"add\":"+s+"}");
	}

	@Override
	public void clr() {
		Client.serverCall("{\"clr\":\"\"}");
	}
}
