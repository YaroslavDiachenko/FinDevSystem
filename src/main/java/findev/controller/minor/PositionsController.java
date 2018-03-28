package findev.controller.minor;

import findev.errorshandling.customexceptions.OccupiedNameException;
import findev.model.Position;
import findev.service.interfaces.IPositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/positions")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
public class PositionsController {
    @Autowired private IPositionService positionService;

    @ApiOperation(value = "get all positions")
    @GetMapping("")
    public ResponseEntity<List<Position>> getAll() {
        List<Position> pl = positionService.getAll();
        if (pl.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(pl, HttpStatus.OK);
    }

    @ApiOperation(value = "get position by id")
    @GetMapping("/{positionId}")
    public ResponseEntity<Position> getById(
            @PathVariable("positionId") Long pId) {
        if (pId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Position p = positionService.getById(pId);
        if (p == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @ApiOperation(value = "create new position")
    @PostMapping("")
    public ResponseEntity<Position> create(
            @RequestBody Position p1) {
        if (p1 == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (positionService.getByName(p1.getName()) != null)
            throw new OccupiedNameException("Position with specified name already exists.");
        p1.setId(null);
        Position p2 = positionService.save(p1);
        return new ResponseEntity<>(p2, HttpStatus.CREATED);
    }

    @ApiOperation(value = "update position")
    @PostMapping("/{positionId}")
    public ResponseEntity<Position> update(
            @PathVariable("positionId") Long pId,
            @RequestBody Position p) {
        if (pId == null || p == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        boolean isExists = positionService.isExists(pId);
        if (!isExists)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        p.setId(pId);
        positionService.save(p);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @ApiOperation(value = "delete position by id")
    @DeleteMapping("/{positionId}")
    public ResponseEntity delete(
            @PathVariable("positionId") Long pId) {
        if (pId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (positionService.getById(pId) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        positionService.delete(pId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}