import React from 'react'

// Material UI component
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import Box from '@mui/material/Box'
// import Rating from '@material-ui/lab/Rating';
import Rating from "@mui/material/Rating"
import TextField from "@mui/material/TextField"
import Button from "@mui/material/Button"


// Material UI icon
import StarIcon from '@mui/icons-material/Star'

const ReviewEdit = () => {
    
    
    
    return (
        <Paper sx={{ p: 2, width: 500 }}>
            <Grid container>

                <Grid item xs={12}>
                    <Rating 
                        name="rating-review"
                        // value={rating}
                        precision={0.1}
                        // getLabelText={getLabelText}
                        // onChange={handleChangeRating}
                        // onChangeActive={handleChangeActiveRating}
                    />
                    {/* {messageRating} */}
                    {/* <Rating
                        name="rating"
                        value={rating}
                        precision={0.1} 
                        onChange={handleChangeRating}
                        onMouseOver={handleMouseOver}
                    /> */}
                    {/* <Box sx={{ width: 200, display: 'flex', alignItems: 'center' }}>
                        <Rating
                            name="hover-feedback"
                            value={value}
                            precision={0.1}
                            getLabelText={getLabelText}
                            onChange={(event, newValue) => {
                                console.log('new value => ', newValue)
                            setValue(newValue);
                            }}
                            onChangeActive={(event, newHover) => {
                                console.log('new hover => ', newHover)
                            setHover(newHover);
                            }}
                            emptyIcon={<StarIcon style={{ opacity: 0.55 }} fontSize="inherit" />}
                        />
                        {value !== null && (
                                <Box sx={{ ml: 2 }}>{labels[hover !== -1 ? hover : value]}</Box>
                            )
                        }
                    </Box> */}
                </Grid>

                <Grid item xs={12}>
                    <TextField
                        id="comment"
                        label="Comment"
                        variant="outlined"
                        margin="normal"
                        required 
                        fullWidth
                        multiline
                        rows={5}
                        // value={comment}

                        // helperText={message.detail}
                        // error={error.detail}
                        
                        // onChange={handleChangeComment}
                        
                        // onBlur={handleBlur}
                    />
                </Grid>

                <Grid item xs={12}>
                    <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                        <Button variant='contained'
                        //  onClick={postReview}
                         >
                            Review
                        </Button>
                    </Box>
                </Grid>

            </Grid>
        </Paper>
    )
}

export default ReviewEdit