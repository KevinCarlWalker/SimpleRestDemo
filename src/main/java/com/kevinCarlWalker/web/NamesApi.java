package com.kevinCarlWalker.web;

import com.kevinCarlWalker.web.exceptions.ExceptionMapper;
import com.kevinCarlWalker.web.Model.Names;
import com.kevinCarlWalker.web.exceptions.BadRequestException;
import com.kevinCarlWalker.web.exceptions.NotFoundException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.kevinCarlWalker.web.Model.NamesValidator.*;

@RestController
@RequestMapping(value = "/name")
public class NamesApi {

  public NamesApi() {
  }

  /**
   * Get
   */
  @ApiOperation(value = "Find Name by ID")
  @ApiResponses({
      @ApiResponse(code = 400, message = "Invalid ID supplied"),
      @ApiResponse(code = 404, message = "Name not found")
  })
  @GetMapping(path = "/{nameId}")
  public Names getName(@PathVariable Long nameId) {
    if (nameId <= 0) {
      throw new NotFoundException(ERROR_INVALID_NAME_ID + nameId, ExceptionMapper.INVALID_NAME);
    }
    return null;
//        mongoService.getRuleset(companyId).orElseThrow(() -> new NotFoundException("Target Assignment Rules not found for company: " + companyId, ExceptionMapper.NO_RULESET_FOUND_FOR_COMPANY));
  }

  /**
   * Post
   */
  @ApiOperation(value = "Create a Name")
  @ApiResponses({
      @ApiResponse(code = 400, message = "Name Already Exists"),
  })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public void postName(@RequestBody Names name) {
    validate(name);
    // if name has id,
    // get name from database
    // if that id doesn't exist in database, create it
    // if that id does exist, treat as put
//    mongoService.insertRuleset(rules);
  }

  /**
   * Put
   */
  @ApiOperation(value = "Update name by ID")
  @ApiResponses({
      @ApiResponse(code = 400, message = "Invalid ID supplied"),
      @ApiResponse(code = 404, message = "Name not found")
  })
  @PutMapping(path = "/{nameId}",  consumes = MediaType.APPLICATION_JSON_VALUE)
  public void putName(@PathVariable Long nameId, @RequestBody Names name) {
    validate(name);
    if ( nameId < 1 || nameId.longValue() != name.getNameId().longValue() ) {
      throw new BadRequestException(ERROR_MISS_MATCHED_NAME_ID, ExceptionMapper.INVALID_NAME);
    }
//    mongoService.updateRuleset(rules);
  }

  /**
   * Delete
   */
  @ApiOperation(value = "Delete Name by ID")
  @ApiResponses({
      @ApiResponse(code = 400, message = "Invalid Name ID supplied"),
      @ApiResponse(code = 404, message = "Name not found")
  })
  @DeleteMapping(path = "/{nameId}")
  public void deleteName(@PathVariable Long nameId) {
  }
}
