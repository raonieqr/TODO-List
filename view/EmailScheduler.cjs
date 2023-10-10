
const nodemailer = require('nodemailer');
const cron = require('node-cron');

class EmailScheduler {
  transporter;
  mailOptions;

  constructor(content) {
    this.transporter = nodemailer.createTransport({
      host: 'smtp.elasticemail.com',
      port: 2525, 
      auth: {
        user: 'botraoni@gmail.com', 
        pass: 'A75991FA33F0786D95639EAC3DFAAC4611E2', 
      },
    });

    this.mailOptions = {
      from: 'botraoni@gmail.com',
      to: 'raoniesilva@gmail.com',
      subject: 'Relatório semanal: TODO-List',
      text: content,
    };
  }

  startWeeklyEmailSchedule() {
    cron.schedule('00 06 * * 1', () => {

      this.transporter.sendMail(this.mailOptions, (error, info) => {
        if (error) {
          console.error('Erro ao enviar o email:', error);
        } else {
          console.log('Email enviado com sucesso:', info.response);
        }
      });
    });
  }
}

module.exports = EmailScheduler