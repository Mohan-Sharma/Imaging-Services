package org.owasp.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
* Clickjacking is an attack that tricks users by showing them an innocuous page but including the real controls from sensitive pages.
* These controls are disguised through the use of background frames that mask off everything
* except the control, so that the user can't tell that they are actually clicking on a sensitive function in some other website.
* How can web applications protect their users from this attack? The typical defense to clickjacking
* is to prevent your pages from being framed. The typical approach to this is to include a "framebreaker" script in every page that
* ensures that the content is not framed. Here's an example of such a script. <script>if (top !=
* self) top.location=location</script> However, there's an alternative approach that may be simpler to implement. 
* Microsoft has now included a defense in IE8 that allows developers to specify that pages should not be framed.
* They use a new (nonstandard) X-FRAME-OPTIONS header to mark responses that shouldn't be framed. There are two options with X-FRAME-OPTIONS. 
* The first is DENY, which prevents everyone from framing the content. The other
* option is SAMEORIGIN, which only allows the current site to frame the content.
* Currently this works in IE8 RC1+, but will not affect users of other browsers until they decide to implement this feature. Firefox users may want
* to look at the protection in NoScript.
* @author PRADYUMNA
*
*/
public class ClickjackFilter implements Filter {

	private String mode = "DENY";

	/**
	 * Add X-FRAME-OPTIONS response header to tell IE8 (and any other browsers who
	 * decide to implement) not to display this content in a frame. For details, please
	 * refer to http://blogs.msdn.com/sdl/archive/2009/02/05/clickjacking-defense-in-ie8.aspx.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
	HttpServletResponse res = (HttpServletResponse) response;
	chain.doFilter(request, response);
	res.addHeader("X-FRAME-OPTIONS", mode);
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) {
	String configMode = filterConfig.getInitParameter("mode");
	if (configMode != null) {
		mode = configMode;
	}
	}

}
