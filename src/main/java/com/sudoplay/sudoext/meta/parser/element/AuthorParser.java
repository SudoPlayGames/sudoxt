package com.sudoplay.sudoext.meta.parser.element;

import com.sudoplay.sudoext.meta.MetaParseException;
import com.sudoplay.sudoext.meta.Meta;
import com.sudoplay.sudoext.meta.parser.AbstractMetaElementParser;
import org.json.JSONObject;

/**
 * Created by codetaylor on 2/18/2017.
 */
public class AuthorParser extends
    AbstractMetaElementParser {

  @Override
  public void parse(JSONObject jsonObject, Meta store) throws MetaParseException {
    store.setAuthor(this.readString("author", jsonObject));
  }
}
