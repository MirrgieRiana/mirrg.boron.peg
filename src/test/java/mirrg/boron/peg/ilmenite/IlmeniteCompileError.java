package mirrg.boron.peg.ilmenite;

public class IlmeniteCompileError extends Exception
{

	public IlmeniteCompileError()
	{
		super();
	}

	public IlmeniteCompileError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IlmeniteCompileError(String message, Throwable cause)
	{
		super(message, cause);
	}

	public IlmeniteCompileError(String message)
	{
		super(message);
	}

	public IlmeniteCompileError(Throwable cause)
	{
		super(cause);
	}

}
