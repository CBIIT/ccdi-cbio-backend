package org.cbioportal.legacy.web;

import static org.cbioportal.legacy.service.FrontendPropertiesServiceImpl.FrontendProperty;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.cbioportal.legacy.service.FrontendPropertiesService;
import org.cbioportal.legacy.service.util.MskWholeSlideViewerTokenGenerator;
import org.cbioportal.legacy.web.util.HttpRequestUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tools.jackson.databind.json.JsonMapper;

@Controller
public class IndexPageController {
  private static final Logger log = LoggerFactory.getLogger(IndexPageController.class);

  @Autowired private FrontendPropertiesService frontendPropertiesService;

  @Autowired private HttpRequestUtils requestUtils;

  @Value("${authenticate}")
  private String[] authenticate;

  @Value("${saml.idp.metadata.entityid:not_defined_in_portalproperties}")
  private String samlIdpEntityId;

  @Value("${msk.whole.slide.viewer.secret.key:}")
  private String wholeSlideViewerKey;

  private final JsonMapper mapper = JsonMapper.builder().build();

  private Map<String, Object> getFrontendProperties(
      HttpServletRequest request, Authentication authentication) {
    String baseUrl = requestUtils.getBaseUrl(request);
    Map<String, Object> properties = new HashMap<>();

    Map<String, String> originalProperties = frontendPropertiesService.getFrontendProperties();

    for (Map.Entry<String, String> entry : originalProperties.entrySet()) {
      String value = entry.getValue();
      if (value != null && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
        properties.put(entry.getKey(), Boolean.valueOf(value));
      } else {
        properties.put(entry.getKey(), value);
      }
    }
    properties.put("base_url", baseUrl);
    properties.put(
        "user_email_address", authentication != null ? authentication.getName() : "anonymousUser");
    properties.put(
        "user_display_name", authentication != null ? authentication.getName() : "anonymousUser");
    // Set MSK slide viewer token at runtime
    properties.put(
        "mskWholeSlideViewerToken",
        getMskWholeSlideViewerToken(wholeSlideViewerKey, authentication));
    return properties;
  }

  private String getMskWholeSlideViewerToken(String secretKey, Authentication authentication) {
    // this token is for the msk portal
    // the token is generated based on users' timestamp to let the slide viewer know whether the
    // token is expired and then decide whether to allow the user to login the viewer
    // every time when we refresh the page or goto the new page, a new token should be generated
    if (secretKey != null) secretKey = secretKey.trim();
    String timeStamp = String.valueOf(System.currentTimeMillis());

    if (authentication != null
        && authentication.isAuthenticated()
        && secretKey != null
        && !secretKey.isEmpty()) {
      return "{ \"token\":\""
          + MskWholeSlideViewerTokenGenerator.generateTokenByHmacSHA256(
              authentication.getName(), secretKey, timeStamp)
          + "\", \"time\":\""
          + timeStamp
          + "\"}";
    } else {
      return null;
    }
  }

  @RequestMapping({"/", "/index", "/index.html", "/study/summary", "/results"})
  public String showIndexPage(
      HttpServletRequest request, Authentication authentication, Model model) {

    String baseUrl = requestUtils.getBaseUrl(request);
    JSONObject postData = requestUtils.getPostData(request);

    model.addAttribute(
        "propertiesJson",
        mapper.writeValueAsString(getFrontendProperties(request, authentication)));
    model.addAttribute(
        "frontendUrl", frontendPropertiesService.getFrontendProperty(FrontendProperty.frontendUrl));
    model.addAttribute("baseUrl", baseUrl);
    model.addAttribute("contextPath", request.getContextPath());
    model.addAttribute(
        "appVersion", frontendPropertiesService.getFrontendProperty(FrontendProperty.app_version));
    model.addAttribute("postData", postData.isEmpty() ? "undefined" : postData);
    model.addAttribute(
        "googleTagManagerId",
        frontendPropertiesService.getFrontendProperty(FrontendProperty.google_tag_manager_id));

    return "index";
  }

  @GetMapping("/config_service")
  public ResponseEntity<?> getConfigService(
      HttpServletRequest request, Authentication authentication) {
    return ResponseEntity.ok(getFrontendProperties(request, authentication));
  }

  public FrontendPropertiesService getFrontendPropertiesService() {
    return frontendPropertiesService;
  }
}
