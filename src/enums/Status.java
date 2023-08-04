package enums;

public enum Status {
	DONE("done", 1),
	DOING("doing", 2),
	TODO("todo", 3);

	String name;
	int value;

	Status(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}
}
