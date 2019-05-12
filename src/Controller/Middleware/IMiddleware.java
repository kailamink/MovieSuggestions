package Controller.Middleware;

public interface IMiddleware<TContext>
{
    public abstract void setNextInChain(IMiddleware iMiddleware);
    public abstract void invokeProcess(TContext context);
    public abstract void process(TContext context);
}
