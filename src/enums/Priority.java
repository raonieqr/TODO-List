package enums;

public enum Priority {
	MUITO_BAIXA("muito baixa", 1),
	BAIXA("baixa", 2),
	MEDIA("média", 3),
	ALTA("alta", 4),
	MUITO_ALTA("muito alta", 5);

	private String name;
	private int value;

	Priority(String name, int value) {
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