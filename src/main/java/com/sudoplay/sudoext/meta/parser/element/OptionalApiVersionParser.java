package com.sudoplay.sudoext.meta.parser.element;

import com.sudoplay.sudoext.meta.Meta;
import com.sudoplay.sudoext.meta.MetaParseException;
import com.sudoplay.sudoext.meta.parser.AbstractMetaElementParser;
import com.sudoplay.sudoext.versioning.VersionRange;
import org.json.JSONObject;

/**
 * Created by codetaylor on 2/18/2017.
 */
public class OptionalApiVersionParser extends
    AbstractMetaElementParser {

  @Override
  public void parse(JSONObject jsonObject, Meta store) throws MetaParseException {
    store.setApiVersionRange(this.readApiVersionRange("api-version", jsonObject));
  }

  private VersionRange readApiVersionRange(String key, JSONObject jsonObject) throws MetaParseException {
    String versionString = jsonObject.optString(key, "[0,)");
    return this.parseVersionRange(versionString, "Invalid api version string: " + versionString);
  }
}
