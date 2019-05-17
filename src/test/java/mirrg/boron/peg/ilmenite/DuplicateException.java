package mirrg.boron.peg.ilmenite;

public class DuplicateException extends IlmeniteCompileError
{

	public DuplicateException()
	{
		super();
	}

	public DuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DuplicateException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public DuplicateException(String message)
	{
		super(message);
	}

	public DuplicateException(Throwable cause)
	{
		super(cause);
	}

}
