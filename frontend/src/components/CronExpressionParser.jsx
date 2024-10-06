/* eslint-disable react/prop-types */
import { useEffect, useState } from "react";

const CronExpressionParser = ({cronExpression}) =>{

    const [parsedExpression, setparsedExpression] = useState("");
  
    useEffect(()=>{
        const res = convertCronToText(cronExpression);
        setparsedExpression(res);
    },[cronExpression])
    


    const convertCronToText = (cron) => {
      const parts = cron.split(' ');
  
      if (parts.length !== 6) {
        return "Invalid cron expression. Cron should have 6 parts (seconds, minutes, hours, day of month, month, day of week).";
      }
  
      const [second, minute, hour, dayOfMonth, month, dayOfWeek] = parts;
  
      const secondText = getSecondText(second);
      const minuteText = getMinuteText(minute);
      const hourText = getHourText(hour);
      const dayOfMonthText = getDayOfMonthText(dayOfMonth);
      const monthText = getMonthText(month);
      const dayOfWeekText = getDayOfWeekText(dayOfWeek);
  
      return `${secondText} ${minuteText} ${hourText} ${dayOfMonthText} ${monthText} ${dayOfWeekText}`.trim();
    };
  
    const getSecondText = (second) => {
      if (second === "*") return "every second";
      return `at second ${second}`;
    };
  
    const getMinuteText = (minute) => {
      if (minute === "*") return "every minute";
      return `at minute ${minute}`;
    };
  
    const getHourText = (hour) => {
      if (hour === "*") return "every hour";
      return `at ${hour}:00`;
    };
  
    const getDayOfMonthText = (dayOfMonth) => {
      if (dayOfMonth === "*") return "every day";
      return `on day ${dayOfMonth} of the month`;
    };
  
    const getMonthText = (month) => {
      if (month === "*") return "every month";
      const months = [
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
      ];
      return `in ${months[parseInt(month) - 1]}`;
    };
  
    const getDayOfWeekText = (dayOfWeek) => {
      if (dayOfWeek === "*") return "";
      const days = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
      return `on ${days[parseInt(dayOfWeek)]}`;
    };
  

  
  
    
  
    return (
      <div>
        <p>{parsedExpression}</p>
      </div>
    );

}

export default CronExpressionParser;