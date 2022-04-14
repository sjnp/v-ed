import React, { useState } from 'react'

// Material UI
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import Box from '@mui/material/Box'
// import Rating from '@material-ui/lab/Rating';
import Rating from "@mui/material/Rating"
import TextField from "@mui/material/TextField"
import Button from "@mui/material/Button"


// icon
import StarIcon from '@mui/icons-material/Star'



const ReviewWrite = ({ dataRating, dataComment }) => {

    // const [ rating, setRating ] = useState(0);

    // const handleChangeRating = (event, newRating) => {
    //     setRating(newRating)
    //     console.log('new rating => ', newRating)
    // }

    // let count = 0

    // const handleMouseOver = (event) => {
    //     ++count
    //     console.log(`${count} => `)
    //     console.log(event.target)
    //     console.log('-'.repeat(30))
    // }

    //----------------------

    // const labels = {
    //     0.5: 'Useless',
    //     1: 'Useless+',
    //     1.5: 'Poor',
    //     2: 'Poor+',
    //     2.5: 'Ok',
    //     3: 'Ok+',
    //     3.5: 'Good',
    //     4: 'Good+',
    //     4.5: 'Excellent',
    //     5: 'Excellent+',
    //   };
      
    //   function getLabelText(value) {
    //     return `${value} Star${value !== 1 ? 's' : ''}, ${labels[value]}`;
    //   }
      
    // const [value, setValue] = useState(2);
    // const [hover, setHover] = useState(-1);

    const [ rating, setRating ] = useState(dataRating || 0)

    const [ messageRating, setMessageRating ] = useState(dataRating || '')

    const handleChangeRating = (event, newRating) => {
        if (newRating === null) return
        setRating(newRating)
    }
    
    const handleChangeActiveRating = (event, newRating) => {
        // if (newRating > 0) {
        //     setMessageRating(newRating)
        // } else {
        //     setMessageRating(rating)
        // }
        const value = newRating > 0 ? newRating : rating
        setMessageRating(value)
    }


    const [ comment, setComment ] = useState(dataComment || '')

    const handleChangeComment = (event) => {
        setComment(event.target.value)
        console.log(event.target.value)
    }

    const postReview = () => {
        if (rating < 0 || rating > 5) {
            alert('rating invalid')
            return
        }

        if (comment.trim().length === 0) {
            alert('comment is required')
            return
        }

        alert('review ok')
    }

    return (
        <Paper sx={{ p: 2, width: 500 }}>
            <Grid container>

                <Grid item xs={12}>
                    <Rating 
                        name="rating-review"
                        value={rating}
                        precision={0.1}
                        // getLabelText={getLabelText}
                        onChange={handleChangeRating}
                        onChangeActive={handleChangeActiveRating}
                    />
                    {messageRating}
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
                        value={comment}
                        // helperText={message.detail}
                        // error={error.detail}
                        onChange={handleChangeComment}
                        // onBlur={handleBlur}
                    />
                </Grid>

                <Grid item xs={12}>
                    <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                        <Button variant='contained' onClick={postReview}>
                            Review
                        </Button>
                    </Box>
                </Grid>

            </Grid>
        </Paper>
    )
}

export default ReviewWrite