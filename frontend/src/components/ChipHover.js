import React, { useState } from 'react'

// component
import Chip from '@mui/material/Chip'

const ChipHover = (props) => {

    const { defaultLabel, hoverLabel, defaultVariant, hoverVariant, defaultColor, hoverColor } = props
    
    const [ isHover, setIsHover ] = useState(false)

    return (
        <div onMouseOver={() => setIsHover(true)} onMouseOut={() => setIsHover(false)}>
        {
            isHover ?
            <Chip
                variant={hoverVariant || 'outlined'} 
                color={hoverColor || 'default'} 
                label={hoverLabel}
            />
            :
            <Chip 
                variant={defaultVariant || 'outlined'} 
                color={defaultColor || 'default'}
                label={defaultLabel}
            />
        }
        </div>
    )
}

export default ChipHover