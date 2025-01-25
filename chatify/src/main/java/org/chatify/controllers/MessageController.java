package org.chatify.controllers;

import org.chatify.http.AppResponse;
import org.chatify.models.requests.AddMessageRequest;
import org.chatify.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("channels/{id}/messages")
    public ResponseEntity<?> getMessagesForChannel(@PathVariable int id){
        try {
            var messages = this.messageService.getMessages(id);

            return AppResponse.success().withCode(HttpStatus.OK).withData(messages).build();
        } catch (IllegalArgumentException e){
            return AppResponse.error().withCode(HttpStatus.BAD_REQUEST).withMessage(e.getMessage()).build();
        } catch (Exception e){
            return AppResponse.error().withCode(HttpStatus.INTERNAL_SERVER_ERROR).withMessage(e.getMessage()).build();
        }
    }

    @PostMapping("channels/{id}/messages")
    public ResponseEntity<?> addMessage(@PathVariable int id, @RequestBody AddMessageRequest request){
        try {
            var msg = this.messageService.sendMessage(id, request);

            return AppResponse.success().withCode(HttpStatus.CREATED).withData(msg).build();
        } catch (IllegalArgumentException e){
            return AppResponse.error().withCode(HttpStatus.BAD_REQUEST).withMessage(e.getMessage()).build();
        } catch (Exception e){
            return AppResponse.error().withCode(HttpStatus.INTERNAL_SERVER_ERROR).withMessage(e.getMessage()).build();
        }
    }
}
