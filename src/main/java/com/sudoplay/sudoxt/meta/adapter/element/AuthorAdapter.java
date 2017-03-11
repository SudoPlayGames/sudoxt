package com.sudoplay.sudoxt.meta.adapter.element;

import com.sudoplay.sudoxt.meta.Meta;
import com.sudoplay.sudoxt.meta.adapter.IMetaAdapter;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Reads author from meta file.
 * <p>
 * Created by codetaylor on 2/18/2017.
 */
public class AuthorAdapter implements
    IMetaAdapter {

  @Override
  public void adapt(JSONObject jsonObject, Meta meta) throws JSONException {
    meta.setAuthor(jsonObject.getString("author"));
  }
}