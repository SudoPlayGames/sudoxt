package com.sudoplay.sudoext.candidate;

/**
 * Created by codetaylor on 2/27/2017.
 */
public interface ICandidateProcessor {

  Candidate process(Candidate candidate) throws CandidateProcessorException;
}
