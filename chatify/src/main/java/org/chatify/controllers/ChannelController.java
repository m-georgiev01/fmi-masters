package org.chatify.controllers;

import org.chatify.http.AppResponse;
import org.chatify.models.requests.*;
import org.chatify.services.ChannelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class ChannelController {
    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping("channels/dm")
    public ResponseEntity<?> searchDMs(@RequestParam String userId) {
        try {
            var dms =  this.channelService.getDMChannelsByUserId(userId);

            return AppResponse.success().withCode(HttpStatus.OK).withData(dms).build();
        } catch (NumberFormatException e) {
            return AppResponse.error().withCode(HttpStatus.BAD_REQUEST).withMessage("UserId was not in the correct format!").build();
        }
    }

    @GetMapping("channels")
    public ResponseEntity<?> searchGroupChannels(@RequestParam String userId) {
        try {
            var gc =  this.channelService.getGroupChannelsByUserId(userId);

            return AppResponse.success().withCode(HttpStatus.OK).withData(gc).build();
        } catch (NumberFormatException e) {
            return AppResponse.error().withCode(HttpStatus.BAD_REQUEST).withMessage("UserId was not in the correct format!").build();
        }
    }

    @PostMapping("channels")
    public ResponseEntity<?> addChannel(@RequestBody CreateChannelRequest request) {
        try {
            var channel = this.channelService.createChannel(request);

            return AppResponse.success().withCode(HttpStatus.CREATED).withData(channel).build();
        } catch (IllegalArgumentException e) {
            return AppResponse.error().withCode(HttpStatus.BAD_REQUEST).withMessage(e.getMessage()).build();
        } catch (Exception e) {
            return AppResponse.error().withCode(HttpStatus.INTERNAL_SERVER_ERROR).withMessage(e.getMessage()).build();
        }
    }

    @DeleteMapping("channels")
    public ResponseEntity<?> deleteChannel(@RequestBody DeleteChannelRequest request) {
        try{
            this.channelService.deleteChannel(request);

            return AppResponse.success().withCode(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return AppResponse.error().withCode(HttpStatus.BAD_REQUEST).withMessage(e.getMessage()).build();
        } catch (Exception e){
            return AppResponse.error().withCode(HttpStatus.INTERNAL_SERVER_ERROR).withMessage(e.getMessage()).build();
        }
    }

    @PutMapping("channels/roles/promote")
    public ResponseEntity<?> upgradeToAdmin(@RequestBody AdminUpgradeRequest request){
        try {
            var updated = this.channelService.upgradeToAdmin(request);

            return AppResponse.success().withCode(HttpStatus.OK).withData(updated).build();
        } catch (IllegalArgumentException e) {
            return AppResponse.error().withCode(HttpStatus.BAD_REQUEST).withMessage(e.getMessage()).build();
        } catch (Exception e){
            return AppResponse.error().withCode(HttpStatus.INTERNAL_SERVER_ERROR).withMessage(e.getMessage()).build();
        }
    }

    @PostMapping("channels/{id}/members")
    public ResponseEntity<?> addNewMember(@PathVariable int id, @RequestBody AddChannelMemberRequest request) {
        try {
            var cu = this.channelService.addUserToChannel(id, request);

            return AppResponse.success().withCode(HttpStatus.CREATED).withData(cu).build();
        } catch (IllegalArgumentException e) {
            return AppResponse.error().withCode(HttpStatus.BAD_REQUEST).withMessage(e.getMessage()).build();
        } catch (Exception e){
            return AppResponse.error().withCode(HttpStatus.INTERNAL_SERVER_ERROR).withMessage(e.getMessage()).build();
        }
    }

    @PutMapping("channels")
    public ResponseEntity<?> renameChannel(@RequestBody RenameChannelRequest request) {
        try {
            var channel = this.channelService.renameChannel(request);

            return AppResponse.success().withData(channel).withCode(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return AppResponse.error().withCode(HttpStatus.BAD_REQUEST).withMessage(e.getMessage()).build();
        } catch (Exception e){
            return AppResponse.error().withCode(HttpStatus.INTERNAL_SERVER_ERROR).withMessage(e.getMessage()).build();
        }
    }

    @DeleteMapping("channels/{id}/members")
    public ResponseEntity<?> removeFromChannel(@PathVariable int id, @RequestBody RemoveFromChannelRequest request) {
        try {
            this.channelService.removeMemberFromChannel(id, request);

            return AppResponse.success().withCode(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return AppResponse.error().withCode(HttpStatus.BAD_REQUEST).withMessage(e.getMessage()).build();
        } catch (Exception e){
            return AppResponse.error().withCode(HttpStatus.INTERNAL_SERVER_ERROR).withMessage(e.getMessage()).build();
        }
    }

    @GetMapping("channels/{id}/members")
    public ResponseEntity<?> getMembers(@PathVariable int id) {
        try{
            var members = this.channelService.getChannelMembers(id);

            return AppResponse.success().withData(members).withCode(HttpStatus.OK).build();
        } catch (Exception e) {
            return AppResponse.error().withCode(HttpStatus.INTERNAL_SERVER_ERROR).withMessage(e.getMessage()).build();
        }
    }
}
