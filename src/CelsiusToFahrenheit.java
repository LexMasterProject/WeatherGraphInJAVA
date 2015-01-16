/**
 *  this class implement DataTransfer interface
 *  function:
 *  transfer from Celsius to Fahrenheit 
 *  
 */

public class CelsiusToFahrenheit implements DataTransfer {

	@Override
	public float transfer(float a) {

		return (a*9/5+32);
	}

}
