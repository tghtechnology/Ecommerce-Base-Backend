package tghtechnology.tiendavirtual.Enums;

public enum TokenActions {
	
	LOGIN,
	VERIFY,
	CHANGE_PASSWORD;
	
	public Boolean is(String other) {
		return this.equals(TokenActions.valueOf(other));
	}
	
}
