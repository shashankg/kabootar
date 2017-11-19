package com.shash.kabootar.commons.filter;

/**
 * @author shashankgautam
 */
public final class UserContext {

    private static final UserContext INSTANCE = new UserContext();

    private ThreadLocal<Context> contextThreadLocal = new ThreadLocal<>();

    /*
     * private constructor for singleton impl
     */
    private UserContext() {
    }

    public static UserContext instance() {
        return INSTANCE;
    }

    void clear() {
        this.contextThreadLocal.remove();
    }

    /*
     * @return
     */
    Context getContextThreadLocal() {
        Context context = this.contextThreadLocal.get();
        if (context == null) {
            context = new Context();
            this.contextThreadLocal.set(context);
        }
        return context;
    }

    /**
     * @return user object from thread local
     */
    public String getUser() {
        return getContextThreadLocal().getItem();
    }
}
