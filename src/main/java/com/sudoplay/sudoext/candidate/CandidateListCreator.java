package com.sudoplay.sudoext.candidate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides a list of candidates.
 * <p>
 * Created by codetaylor on 2/20/2017.
 */
public class CandidateListCreator implements
    ICandidateListCreator {

  private static final Logger LOG = LoggerFactory.getLogger(CandidateListCreator.class);

  private ICandidateProvider[] candidateLocators;

  public CandidateListCreator(
      ICandidateProvider[] candidateLocators
  ) {
    this.candidateLocators = candidateLocators;
  }

  @Override
  public List<Candidate> createCandidateList() {
    LOG.debug("Entering createCandidateList()");
    LOG.debug(
        "Looking for candidates using [{}] locators",
        this.candidateLocators.length
    );
    LOG.trace("...candidateLocators=[{}]", Arrays.toString(this.candidateLocators));

    List<Candidate> result = new ArrayList<>();

    for (ICandidateProvider locator : this.candidateLocators) {

      try {
        result.addAll(locator.getCandidates());

      } catch (IOException e) {
        LOG.error("Error locating candidates with [{}]", locator, e);
      }
    }

    LOG.info("Found a total of [{}] candidates", result.size());
    LOG.debug("Leaving createCandidateList()");
    LOG.trace("...[{}]", result);
    return result;
  }
}
