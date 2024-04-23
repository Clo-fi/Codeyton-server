package clofi.codeython.room.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import clofi.codeython.member.service.dto.CustomMemberDetails;
import clofi.codeython.room.controller.response.AllRoomResponse;
import clofi.codeython.room.controller.response.CreateRoomResponse;
import clofi.codeython.room.controller.response.RoomResponse;
import clofi.codeython.room.domain.request.CreateRoomRequest;
import clofi.codeython.room.service.RoomService;
import clofi.codeython.room.service.request.WaitRoomRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/api/rooms/{roomId}")
    public RoomResponse joinRoomWithPassword(
        @RequestBody WaitRoomRequest request,
        @PathVariable("roomId") Long roomId,
        @AuthenticationPrincipal CustomMemberDetails userDetails) {
        return roomService.joinRoomWithPassword(request, roomId, userDetails);
    }

    @PostMapping("/api/rooms/direct/{inviteCode}")
    public RoomResponse joinRoomWithInviteCode(
        @PathVariable("inviteCode") String inviteCode,
        @AuthenticationPrincipal CustomMemberDetails userDetails) {
        return roomService.joinRoomWithInviteCode(inviteCode, userDetails);
    }

    @PostMapping("/api/rooms")
    public ResponseEntity<CreateRoomResponse> createRoom(
        @RequestBody @Valid CreateRoomRequest createRoomRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(roomService.createRoom(createRoomRequest));
    }

    @GetMapping("/api/rooms")
    public ResponseEntity<List<AllRoomResponse>> getAllRoom() {

        return ResponseEntity.ok(roomService.getAllRoom());

    }
}