package com.kevinCarlWalker.web;

import com.kevinCarlWalker.web.exceptions.ExceptionMapper;
import com.kevinCarlWalker.web.Model.Names;
import com.kevinCarlWalker.web.exceptions.BadRequestException;
import com.kevinCarlWalker.web.exceptions.NotFoundException;
import com.kevinCarlWalker.web.DataAccess.DatabaseHelpers;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.kevinCarlWalker.web.Model.NamesValidator.*;

@RestController
@RequestMapping(value = "/name")
public class NamesApi {

  /**
   * Get
   */
  @ApiOperation(value = "Find Name by ID")
  @ApiResponses({
      @ApiResponse(code = 400, message = "Invalid ID supplied"),
      @ApiResponse(code = 404, message = "Name not found")
  })
  @GetMapping(path = "/{nameId}")
  public Names getName(@PathVariable Integer nameId) {
    if (nameId <= 0) {
      throw new NotFoundException(ERROR_INVALID_NAME_ID + nameId, ExceptionMapper.INVALID_NAME);
    }
    Names name = DatabaseHelpers.getName( nameId);
    if(name.getNameId() < 1){
      throw new NotFoundException(ERROR_INVALID_NAME_ID + nameId, ExceptionMapper.INVALID_NAME);
    }
    return name;
  }

  /**
   * Post
   * Permissive as possible. Posting the same thing twice is allowed. Posting with an existing id updates
   */
  @ApiOperation(value = "Create a Name")
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Names postName(@RequestBody Names name) {
    validate(name);

    // if name has id,
    if( name.getNameId() != null && name.getNameId() > 0){
      Names nameFromDb = DatabaseHelpers.getName( name.getNameId() );
      //and it already exists in database
      if(nameFromDb.getNameId().equals(name.getNameId())){
        //treat it like a put
        return putName( name.getNameId(), name);
      }
    }
    return DatabaseHelpers.createName( name );
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
  public Names putName(@PathVariable Integer nameId, @RequestBody Names name) {
    validate(name);
    if ( nameId < 1 || nameId.intValue() != name.getNameId().intValue() ) {
      throw new BadRequestException(ERROR_MISS_MATCHED_NAME_ID, ExceptionMapper.INVALID_NAME);
    }
    return DatabaseHelpers.updateName(name);
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
  public void deleteName(@PathVariable Integer nameId) {
    DatabaseHelpers.deleteName(nameId);
  }
}
