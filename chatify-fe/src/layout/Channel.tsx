import React from "react";
import { DefaultProps } from "../models/DefaultProps";
import { Box, TextField, Typography } from "@mui/material";
import { observer } from "mobx-react";

@observer
export class Channel extends React.Component<DefaultProps> {
    render(): React.ReactNode {
        const {selectedChannel, messages, user, sendMessage, message, setMessage} = this.props.state;

        const formatDate = (date: string): string => {
            const parsedDate = new Date(date);

            return parsedDate.toLocaleString('en-GB', {
                hour: '2-digit',
                minute: '2-digit',
                day: '2-digit',
                month: '2-digit',
                year: 'numeric'
              });
        };

        return(
            <Box sx={{flexGrow: "1"}}>
                {selectedChannel ? 
                    <Box sx={{ display: "flex", flexDirection: "column", height: "100%"}}>
                        <Box color="#1E293B" flexGrow="1" sx={{display: "flex", flexDirection: "column"}}>
                            <Typography color="inherit" variant="h4" borderBottom="3px solid #1E293B" py="15px" mb="20px">
                                {selectedChannel.name}
                            </Typography>
                            <Box maxHeight="50vh" minHeight="50vh" overflow="auto">
                                <Box>
                                {messages.map((m) => 
                                    <Box display="flex" key={m.id} p="0 30px 15px 30px" justifyContent={m.sender.id === user?.id ? "right" : "left"} textAlign={m.sender.id === user?.id ? "right" : "left"}>
                                        <Box color="#1E293B" sx={{border: "1px solid ", borderRadius: "10px", p: "5px 10px"}}>
                                            <Typography maxWidth="250px" variant="body1" color="inherit" mb="7px">
                                                {m.content}
                                            </Typography>
                                            <Typography variant="body2" color="inherit" fontStyle="italic">
                                                Sent by: {m.sender.id == user?.id ? "Me" : m.sender.username}
                                            </Typography>
                                            <Typography variant="body2" color="inherit" fontStyle="italic">
                                                {formatDate(m.timestamp)}
                                            </Typography>
                                        </Box>
                                    </Box>
                                )}
                                </Box>
                               
                            </Box>
                        </Box>
                        <Box flexGrow="1">
                            <Box sx={{display: "flex", alignItems: "center", gap: "5px", mx: 1}}>
                                <TextField
                                    variant="outlined"
                                    margin="normal"
                                    value={message}
                                    fullWidth
                                    onChange={(e) => setMessage(e.target.value)}
                                    sx={{
                                        '& label.Mui-focused': {
                                            color: '#1E293B',
                                        },
                                        '& .MuiOutlinedInput-root': {
                                            '&.Mui-focused fieldset': {
                                            borderColor: '#1E293B',
                                            },
                                            '& fieldset': {
                                            borderColor: '#1E293B',
                                            },
                                            '&:hover fieldset': {
                                            borderColor: '#1E293B',
                                            },
                                        },
                                        '& .MuiInputLabel-root': {
                                            color: '#1E293B',
                                        },
                                        '& input': {
                                            color: '#1E293B',
                                        },
                                    }}
                                />
                                <Typography color="#1E293B" variant="body1" sx={{cursor: "pointer", ":hover": {textDecoration: "underline"}}} onClick={sendMessage}>Send</Typography>
                            </Box>
                        </Box>
                    </Box> :
                    <Box height="100%" display="flex" justifyContent="center" sx={{alignItems: "center"}}>
                        <Typography flexGrow="1" variant="h4" color="#1E293B">Select a channel</Typography>
                    </Box>
                }
            </Box>
        )
    }
}