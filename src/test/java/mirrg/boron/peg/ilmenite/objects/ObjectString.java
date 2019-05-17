package mirrg.boron.peg.ilmenite.objects;

import java.util.function.Consumer;

public final class ObjectString extends ObjectScalar
{

	public final String value;

	public ObjectString(String value)
	{
		this.value = value;
	}

	@Override
	public void stringify(Consumer<String> stringBuilder)
	{
		stringBuilder.accept("\"");
		stringBuilder.accept(value); // TODO
		stringBuilder.accept("\"");
	}

}
