import { DefaultProps } from "../models/DefaultProps";
import { Box, TextField, Typography } from "@mui/material";
import { observer } from "mobx-react";
import React from "react";

@observer
export class Sidebar extends React.Component<DefaultProps> {
    render(): React.ReactNode {
        return (
            <Box sx={{ backgroundColor: "#1E293B", minWidth: "300px", display: "flex", flexDirection: "column", py: "3px", justifyContent: "space-between"}}>
                <Box sx={{flexGrow: "1"}}>
                    <Typography variant="h5">
                        Channels
                    </Typography>
                    { this.props.state.channels.map(c => 
                        <Typography key={c.id} onClick={() => this.props.state.setSelectedChannel(c)} sx={{ cursor: "pointer", ":hover": {textDecoration: "underline"}}}>{c.name}</Typography>
                    )}
                </Box>
                
                <Box sx={{flexGrow: "1"}}>
                    <Typography variant="h5">
                        DMs
                    </Typography>
                    { this.props.state.dms.map(c => 
                        <Typography key={c.id} onClick={() => this.props.state.setSelectedChannel(c)} sx={{ cursor: "pointer", ":hover": {textDecoration: "underline"}}}>{c.name}</Typography>
                    )}
                </Box>

                <Box sx={{flexGrow: "1", maxHeight: "168px"}}>
                    <Typography variant="h5">
                        Search for Users
                    </Typography>
                    <Box >
                        {!this.props.state.usersSearch ? 
                            <Box display="flex" justifyContent="center" gap="10px" alignItems="center">
                                <TextField
                                    label="Users"
                                    variant="outlined"
                                    margin="normal"
                                    value={this.props.state.userInput}
                                    onChange={(e) => this.props.state.setUserSearchInput(e.target.value)}
                                    sx={{
                                        width: "60%",
                                        '& label.Mui-focused': {
                                            color: '#ffffffde',
                                        },
                                        '& .MuiOutlinedInput-root': {
                                            '&.Mui-focused fieldset': {
                                            borderColor: '#ffffffde',
                                            },
                                            '& fieldset': {
                                            borderColor: '#ffffffde',
                                            },
                                            '&:hover fieldset': {
                                            borderColor: '#ffffffde',
                                            },
                                        },
                                        '& .MuiInputLabel-root': {
                                            color: '#ffffffde',
                                        },
                                        '& input': {
                                            color: '#ffffffde',
                                        },
                                    }}
                                />
                                <Typography variant="body1" sx={{cursor: "pointer", ":hover": {textDecoration: "underline"}}} onClick={this.props.state.searchUsers}>Search</Typography>
                            </Box> :
                            <></>
                        }
                        
                        <Box>
                            {this.props.state.usersSearch ?
                                <Box>
                                    <Box maxHeight="100px" overflow="auto">
                                        {this.props.state.usersSearch.map((u) => 
                                            <Box>
                                                <Typography sx={{cursor: "pointer", ":hover": {textDecoration: "underline"}}} onClick={() => this.props.state.addFriend(u.id)}>{u.username}</Typography>
                                            </Box>
                                        )}
                                    </Box>
                                    <Typography variant="body1" sx={{mt: 1, cursor: "pointer", ":hover": {textDecoration: "underline"}}} onClick={this.props.state.clearUsersSearch}>Cancel</Typography>
                                </Box>  :
                                <></>
                            }
                        </Box>
                    </Box>
                </Box>
            </Box>
        )
    }
}