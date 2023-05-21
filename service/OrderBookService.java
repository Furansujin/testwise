package com.furansujin.brainiac.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface OrderBookService {

  void updateBitfinex(String message) throws JsonProcessingException;

  void updateKraken(String message) throws JsonProcessingException;

  void updateKrakenEur(String message) throws JsonProcessingException;
}
