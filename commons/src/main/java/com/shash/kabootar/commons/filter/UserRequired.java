package com.shash.kabootar.commons.filter;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author shashankgautam
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface UserRequired {
}
