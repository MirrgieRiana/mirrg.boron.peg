package mirrg.boron.peg.ilmenite.objects;

import java.util.function.Consumer;

import mirrg.boron.util.struct.ImmutableArray;

public final class ObjectVector extends ObjectValue
{

	public static final ObjectVector EMPTY = new ObjectVector(ImmutableArray.of());

	public final ImmutableArray<ObjectScalar> array;

	public ObjectVector(ObjectScalar... array)
	{
		this.array = ImmutableArray.of(array);
	}

	public ObjectVector(ImmutableArray<ObjectScalar> array)
	{
		this.array = array;
	}

	@Override
	public void stringify(Consumer<String> stringBuilder)
	{
		stringBuilder.accept("(");
		boolean isFirst = true;
		for (ObjectScalar scalar : array) {
			if (isFirst) {
				isFirst = false;
			} else {
				stringBuilder.accept(",");
			}
			scalar.stringify(stringBuilder);
		}
		stringBuilder.accept(")");
	}

	public static ObjectVector pack(ObjectValue valueIn)
	{
		if (valueIn instanceof ObjectVector) {
			return (ObjectVector) valueIn;
		} else if (valueIn instanceof ObjectScalar) {
			return new ObjectVector(ImmutableArray.of((ObjectScalar) valueIn));
		} else {
			throw new AssertionError();
		}
	}

}
