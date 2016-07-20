package views;

public class Brand {
private String name;
private String iconpath;
private String frist_name;

public Brand(String name, String iconpath, String frist_name) {
	super();
	this.name = name;
	this.iconpath = iconpath;
	this.frist_name = frist_name;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getIconpath() {
	return iconpath;
}
public void setIconpath(String iconpath) {
	this.iconpath = iconpath;
}
public String getFrist_name() {
	return frist_name;
}
public void setFrist_name(String frist_name) {
	this.frist_name = frist_name;
}

}
